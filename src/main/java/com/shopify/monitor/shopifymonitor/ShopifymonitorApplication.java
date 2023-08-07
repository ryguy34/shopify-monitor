package com.shopify.monitor.shopifymonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableFeignClients(basePackages = "com.shopify.monitor.shopifymonitor.feignclient")
@ImportAutoConfiguration(FeignAutoConfiguration.class)
public class ShopifymonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopifymonitorApplication.class, args);
    }

}
