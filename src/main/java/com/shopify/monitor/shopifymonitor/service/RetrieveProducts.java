package com.shopify.monitor.shopifymonitor.service;

import com.shopify.monitor.shopifymonitor.feignclient.ShopifyProductsFeignClient;
import com.shopify.monitor.shopifymonitor.persistance.model.ShopifyStoreInventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RetrieveProducts {

    @Autowired
    private ShopifyProductsFeignClient productsFeignClient;

    public void retrieveProducts(String siteName) {
        ShopifyStoreInventory storeInventory = productsFeignClient.getProducts();
        log.info("Store Inventory: {}", storeInventory);
    }
}
