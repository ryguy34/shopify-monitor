package com.shopify.monitor.shopifymonitor.api;

import com.shopify.monitor.shopifymonitor.api.vo.ShopifyStoreInventoryVO;
import com.shopify.monitor.shopifymonitor.persistance.model.Product;
import com.shopify.monitor.shopifymonitor.service.RetrieveProducts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/shopify/notification")
public class DbInteractionsApi {

    @Autowired
    private RetrieveProducts retrieveProducts;

    @GetMapping(value = "/products")
    public ResponseEntity<ShopifyStoreInventoryVO> getAllProducts() {
        ShopifyStoreInventoryVO products = retrieveProducts.retrieveProducts("");
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product")
    public ResponseEntity<Product> getProduct(@RequestParam String id) {
        Product product = null;
        log.info("Product id: {}", id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
