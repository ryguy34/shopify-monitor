package com.shopify.monitor.shopifymonitor.feignclient;

import com.shopify.monitor.shopifymonitor.api.vo.ShopifyStoreInventoryVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "shopifyclient", url = "${shopify.site-url.undefeated}")
public interface ShopifyProductsFeignClient {

    @GetMapping(value = "/products.json")
    ResponseEntity<ShopifyStoreInventoryVO> getProducts(@RequestParam(value = "limit") int limit, @RequestParam(value = "page") int page);
}