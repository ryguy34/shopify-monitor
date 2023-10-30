package com.shopify.monitor.shopifymonitor.service;

import com.shopify.monitor.shopifymonitor.api.vo.ProductVO;
import com.shopify.monitor.shopifymonitor.api.vo.ShopifyStoreInventoryVO;
import com.shopify.monitor.shopifymonitor.api.vo.VariantVO;
import com.shopify.monitor.shopifymonitor.mappings.ShopifyProductMapper;
import com.shopify.monitor.shopifymonitor.mappings.ShopifyVariantMapper;
import com.shopify.monitor.shopifymonitor.persistance.model.Product;
import com.shopify.monitor.shopifymonitor.persistance.model.Variant;
import com.shopify.monitor.shopifymonitor.persistance.repository.ProductRepository;
import com.shopify.monitor.shopifymonitor.persistance.repository.VariantRepository;
import com.shopify.monitor.shopifymonitor.utility.ShopifyUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class UpdateProductService {

    @Autowired
    private RetrieveProductsService retrieveProductsService;

    @Autowired
    private ShopifyProductMapper shopifyProductMapper;

    @Autowired
    private ShopifyVariantMapper shopifyVariantMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private ShopifyUtility shopifyUtility;

    @Autowired
    private UpdateVariantService updateVariantService;

    @Async("shopifySiteTaskExecutor")
    public CompletableFuture<Void> updateProducts(String siteUrl, boolean isFirstRun) {
        log.info("Site: {}", siteUrl);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String siteName = shopifyUtility.stripSiteName(siteUrl);
        ResponseEntity<ShopifyStoreInventoryVO> storeInventoryEntity = retrieveProductsService.retrieveProducts(siteUrl);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        if (storeInventoryEntity.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            // TODO: send discord notification
            return CompletableFuture.completedFuture(null);
        }
        ShopifyStoreInventoryVO storeInventory = storeInventoryEntity.getBody();
        long count = productRepository.count();
        log.info("Count {}", count);

        if (isFirstRun || count == 0) {
            // save all products and variants
            List<VariantVO> variantVOList = new ArrayList<>();
            List<Product> products = shopifyProductMapper.map(storeInventory.getProducts(), siteName);
            log.debug("Mapped db products: {}", products);

            for (ProductVO product : storeInventory.getProducts()) {
                variantVOList.addAll(product.getVariants());
            }

            List<Variant> variants = shopifyVariantMapper.map(variantVOList);
            variants = shopifyUtility.cleanVariantData(variants);
            log.debug("Mapped db variants: size {} {}", variants.size(), variants);

            productRepository.saveAll(products);
            variantRepository.saveAll(variants);
        } else {
            for (ProductVO p : storeInventory.getProducts()) {
                CompletableFuture<Void> future = updateVariantService.updateVariants(p, siteName);
                futures.add(future);
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }

        stopWatch.stop();
        log.info("Processing time: {} seconds for {}", stopWatch.getTotalTimeSeconds(), siteName);
        return CompletableFuture.completedFuture(null);
    }
}
