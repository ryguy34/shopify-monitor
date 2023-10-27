package com.shopify.monitor.shopifymonitor.feignclient;

import com.shopify.monitor.shopifymonitor.api.vo.ShopifyStoreInventoryVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

// TODO: fix this so the url can be passed in
@FeignClient(value = "shopifyclient", url = "https://this-is-a-placeholder.com")
public interface ShopifyProductsFeignClient {

    @GetMapping(value = "/products.json")
    ResponseEntity<ShopifyStoreInventoryVO> getProducts(URI baseUrl, @RequestParam(value = "limit") int limit, @RequestParam(value = "page") int page);
}