package com.shopify.monitor.shopifymonitor.utility;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "site")
public class SiteUrls {
    private List<Object> urls;
}
