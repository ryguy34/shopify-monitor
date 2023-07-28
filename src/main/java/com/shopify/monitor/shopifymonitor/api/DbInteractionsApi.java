package com.shopify.monitor.shopifymonitor.api;

import com.shopify.monitor.shopifymonitor.persistance.model.Product;
import com.shopify.monitor.shopifymonitor.persistance.model.ShopifyStoreInventory;
import lombok.extern.slf4j.Slf4j;
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

//    @Autowired
//    private
    //TODO: make delete, update

    @GetMapping(value = "/products")
    public ResponseEntity<ShopifyStoreInventory> getAllProducts() {
        ShopifyStoreInventory products = null;
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/product")
    public ResponseEntity<Product> getProduct(@RequestParam String id) {
        Product product = null;
        log.info("Product id: {}", id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
