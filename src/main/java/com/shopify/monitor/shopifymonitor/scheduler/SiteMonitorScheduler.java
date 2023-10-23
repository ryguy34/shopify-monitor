package com.shopify.monitor.shopifymonitor.scheduler;

import com.shopify.monitor.shopifymonitor.api.vo.ProductVO;
import com.shopify.monitor.shopifymonitor.api.vo.ShopifyStoreInventoryVO;
import com.shopify.monitor.shopifymonitor.api.vo.VariantVO;
import com.shopify.monitor.shopifymonitor.mappings.ShopifyProductMapper;
import com.shopify.monitor.shopifymonitor.mappings.VariantMapper;
import com.shopify.monitor.shopifymonitor.persistance.model.Product;
import com.shopify.monitor.shopifymonitor.persistance.model.Variant;
import com.shopify.monitor.shopifymonitor.persistance.repository.ProductRepository;
import com.shopify.monitor.shopifymonitor.persistance.repository.VariantRepository;
import com.shopify.monitor.shopifymonitor.service.RetrieveProducts;
import com.shopify.monitor.shopifymonitor.utility.ShopifyUtility;
import com.shopify.monitor.shopifymonitor.utility.SiteUrls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SiteMonitorScheduler {

    @Autowired
    private RetrieveProducts retrieveProducts;

    @Autowired
    private ShopifyProductMapper shopifyProductMapper;

    @Autowired
    private VariantMapper variantMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private ShopifyUtility shopifyUtility;

    @Autowired
    private SiteUrls siteUrls;

    private boolean isFirstRun = true;

    // TODO: make this method a cron job
    public void monitorSite() {
        String siteUrl = String.valueOf(siteUrls.getUrls().get(0));
        ShopifyStoreInventoryVO storeInventory = retrieveProducts.retrieveProducts(siteUrl);
        log.info("First Run: {}", isFirstRun);

        if (isFirstRun) {
            // save all products
            List<VariantVO> variantVOList = new ArrayList<>();
            String siteName = shopifyUtility.stripSiteName(siteUrl);
            log.debug("Site: {}", siteName);

            List<Product> products = shopifyProductMapper.map(storeInventory.getProducts(), siteName);
            log.debug("Mapped db products: {}", products);

            for (ProductVO product : storeInventory.getProducts()) {
                variantVOList.addAll(product.getVariants());
            }

            List<Variant> variants = variantMapper.map(variantVOList);
            log.debug("Mapped db variants: size {} {}", variants.size(), variants);

            productRepository.saveAll(products);
            variantRepository.saveAll(variants);

            isFirstRun = false;
        } else {

            // TODO: not first run so check for updates
        }
    }
}
