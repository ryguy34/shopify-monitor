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

    public ShopifyStoreInventory retrieveProducts() {
        ShopifyStoreInventory storeInventory = null;
        ShopifyStoreInventory additionalPages = null;
        int i = 0;

        do {
            i++;
            if (i == 1) {   // first page
                // TODO: need to throw an exception if HTTP_STATUS: 403
                storeInventory = productsFeignClient.getProducts(250, i);
            } else {
                additionalPages = productsFeignClient.getProducts(250, i);
                storeInventory.getProducts().addAll(additionalPages.getProducts());
            }
        } while (i == 1 || !additionalPages.getProducts().isEmpty());

        log.info("Store Inventory: {}", storeInventory);
        log.info("Products size: {}", storeInventory.getProducts().size());

        return storeInventory;
    }
}
