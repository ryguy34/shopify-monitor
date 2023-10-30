package com.shopify.monitor.shopifymonitor.service;

import com.shopify.monitor.shopifymonitor.api.vo.ShopifyStoreInventoryVO;
import com.shopify.monitor.shopifymonitor.feignclient.ShopifyProductsClient;
import com.shopify.monitor.shopifymonitor.utility.ShopifyUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Objects;

@Service
@Slf4j
public class RetrieveProductsService {

    @Autowired
    private ShopifyProductsClient shopifyProductsClient;

    @Autowired
    private ShopifyUtility shopifyUtility;

    public ResponseEntity<ShopifyStoreInventoryVO> retrieveProducts(String siteUrl) {
        ResponseEntity<ShopifyStoreInventoryVO> storeInventory = null;
        ResponseEntity<ShopifyStoreInventoryVO> additionalPages = null;
        int i = 0;

        do {
            i++;
            if (i == 1) {   // first page
                storeInventory = shopifyProductsClient.getProducts(URI.create(siteUrl), 250, i);
                if (storeInventory.getStatusCode().equals(HttpStatus.FORBIDDEN)) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            } else {
                additionalPages = shopifyProductsClient.getProducts(URI.create(siteUrl), 250, i);
                Objects.requireNonNull(storeInventory.getBody()).getProducts().addAll(Objects.requireNonNull(additionalPages.getBody()).getProducts());
            }
        } while (i == 1 || !additionalPages.getBody().getProducts().isEmpty());

        if (storeInventory.getBody() != null) {
            storeInventory.getBody().setStoreName(shopifyUtility.stripSiteName(siteUrl));
        }

        log.debug("Products size: {}", storeInventory.getBody().getProducts().size());

        return storeInventory;
    }
}
