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
import java.util.Optional;

@Slf4j
@Service
public class UpdateProducts {

    @Autowired
    private RetrieveProducts retrieveProducts;

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

    @Async
    public void updateProducts(String siteUrl, boolean isFirstRun) {
        log.info("Site: {}", siteUrl);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String siteName = shopifyUtility.stripSiteName(siteUrl);
        ResponseEntity<ShopifyStoreInventoryVO> storeInventoryEntity = retrieveProducts.retrieveProducts(siteUrl);

        if (storeInventoryEntity.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
            // TODO: send discord notification
            return;
        }
        ShopifyStoreInventoryVO storeInventory = storeInventoryEntity.getBody();
        long count = productRepository.count();
        log.info("Count {}", count);

        if (isFirstRun) {
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
            // get store inventory variants and get all db variants for that store
            for (ProductVO p : storeInventory.getProducts()) {
                String productId = p.getId();

                Optional<Product> product = productRepository.findById(productId);
                if (product.isEmpty()) {
                    // is the product completely new
                    log.info("*** New product found: {} ***", p.getTitle());
                    Product mappedProduct = shopifyProductMapper.mapProduct(p, siteName);
                    productRepository.save(mappedProduct);

                    List<Variant> newProductVariants = shopifyVariantMapper.map(p.getVariants());
                    newProductVariants = shopifyUtility.cleanVariantData(newProductVariants);
                    variantRepository.saveAll(newProductVariants);

                    // TODO: send discord notification
                } else {
                    List<VariantVO> currentStoreVariants = p.getVariants();
                    List<Variant> savedProductVariants = variantRepository.findAllByProductId(productId);

                    updateDbAndSendNotification(currentStoreVariants, savedProductVariants);
                }
            }
        }

        stopWatch.stop();
        log.info("Processing time: {} seconds for {}", stopWatch.getTotalTimeSeconds(), siteName);
    }

    private void updateDbAndSendNotification(List<VariantVO> currentStoreVariants, List<Variant> savedProductVariants) {
        for (final VariantVO currentStoreVariant : currentStoreVariants) {
            for (Variant savedVariant : savedProductVariants) {
                if (currentStoreVariant.getId().equals(savedVariant.getId())) {
                    if (Boolean.TRUE.equals(currentStoreVariant.getAvailable()) && Boolean.FALSE.equals(savedVariant.getAvailable())) {
                        // restocked
                        log.info("*** Restocked item: {} ***", savedVariant.getTitle());
                        savedVariant.setAvailable(true);
                        savedVariant.setUpdatedAt(currentStoreVariant.getUpdatedAt());
                        savedVariant = shopifyUtility.cleanVariantData(savedVariant);
                        variantRepository.save(savedVariant);

                        // TODO: send discord notification
                    } else if (Boolean.FALSE.equals(currentStoreVariant.getAvailable()) && Boolean.TRUE.equals(savedVariant.getAvailable())) {
                        // out of stock
                        log.info("*** OOS: {} ***", savedVariant.getTitle());
                        savedVariant.setAvailable(false);
                        savedVariant.setUpdatedAt(currentStoreVariant.getUpdatedAt());
                        variantRepository.save(savedVariant);
                    }
                }
            }
        }
    }
}
