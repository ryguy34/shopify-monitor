package com.shopify.monitor.shopifymonitor.feignclient;

import com.shopify.monitor.shopifymonitor.persistance.model.ShopifyStoreInventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "shopifyclient", url = "https://undefeated.com/")
public interface ShopifyProductsFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/products.json")
    ShopifyStoreInventory getProducts(@RequestParam(value="limit") int limit, @RequestParam(value="page") int page);
}
