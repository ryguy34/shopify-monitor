package com.shopify.monitor.shopifymonitor.api;

import com.shopify.monitor.shopifymonitor.api.vo.ShopifyStoreInventoryVO;
import com.shopify.monitor.shopifymonitor.persistance.model.Product;
import com.shopify.monitor.shopifymonitor.persistance.repository.ProductRepository;
import com.shopify.monitor.shopifymonitor.persistance.repository.VariantRepository;
import com.shopify.monitor.shopifymonitor.scheduler.SiteMonitorScheduler;
import com.shopify.monitor.shopifymonitor.service.RetrieveProducts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/shopify/notification")
public class DbInteractionsApi {

    @Autowired
    private RetrieveProducts retrieveProducts;

    @Autowired
    private SiteMonitorScheduler siteMonitorScheduler;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/products")
    public ResponseEntity<ShopifyStoreInventoryVO> getAllProducts(@RequestParam String siteName) {
        siteName = "https://" + siteName + ".com";
        ShopifyStoreInventoryVO products = retrieveProducts.retrieveProducts(siteName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(value = "/process")
    public ResponseEntity<HttpStatus> processInventory() {
        siteMonitorScheduler.monitorSite();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/findproduct")
    public ResponseEntity<Product> getProduct(@RequestParam String id) {
        Product product = null;
        log.info("Product id: {}", id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping(value = "/deleteallproducts")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        productRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/deleteallvariants")
    public ResponseEntity<HttpStatus> deleteAllVariants() {
        variantRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
