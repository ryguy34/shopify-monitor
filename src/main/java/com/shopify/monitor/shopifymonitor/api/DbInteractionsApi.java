package com.shopify.monitor.shopifymonitor.api;

import com.shopify.monitor.shopifymonitor.api.vo.ShopifyStoreInventoryVO;
import com.shopify.monitor.shopifymonitor.persistance.model.Product;
import com.shopify.monitor.shopifymonitor.persistance.model.Variant;
import com.shopify.monitor.shopifymonitor.persistance.repository.ProductRepository;
import com.shopify.monitor.shopifymonitor.persistance.repository.VariantRepository;
import com.shopify.monitor.shopifymonitor.scheduler.SiteMonitorScheduler;
import com.shopify.monitor.shopifymonitor.service.RetrieveProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Profile("local")
@RestController
@RequestMapping(value = "/shopify/notification")
public class DbInteractionsApi {

    @Autowired
    private RetrieveProductsService retrieveProductsService;

    @Autowired
    private SiteMonitorScheduler siteMonitorScheduler;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/products")
    public ResponseEntity<ShopifyStoreInventoryVO> getAllProducts(@RequestParam String siteName) {
        siteName = "https://" + siteName + ".com";
        ShopifyStoreInventoryVO products = retrieveProductsService.retrieveProducts(siteName).getBody();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(value = "/process")
    public ResponseEntity<HttpStatus> processInventory() {
        siteMonitorScheduler.monitorSite();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/findproduct")
    public ResponseEntity<Product> getProduct(@RequestParam String id) {
        log.info("Product id: {}", id);

        Optional<Product> product = productRepository.findById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/findvariant")
    public ResponseEntity<Variant> getVariant(@RequestParam String id) {
        log.info("Variant id: {}", id);

        Optional<Variant> variant = variantRepository.findById(id);
        return variant.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/findvariantsbyproductid")
    public ResponseEntity<List<Variant>> getVariantsByProductId(@RequestParam String id) {
        log.info("Product id: {}", id);

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            List<Variant> variants = variantRepository.findAllByProductId(product.get().getId());
            return new ResponseEntity<>(variants, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
