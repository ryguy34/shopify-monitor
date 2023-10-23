package com.shopify.monitor.shopifymonitor.utility;

import org.springframework.stereotype.Component;

@Component
public class ShopifyUtility {

    public String stripSiteName(String url) {
        return url.replace("https://", "").replace(".com", "");
    }
}
