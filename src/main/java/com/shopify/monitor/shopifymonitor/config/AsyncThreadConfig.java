package com.shopify.monitor.shopifymonitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncThreadConfig {

    // TODO: need to fine tune these configs
    @Bean(name = "shopifySiteTaskExecutor")
    public ThreadPoolTaskExecutor shopifySiteTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(30);
        threadPoolTaskExecutor.setThreadNamePrefix("shopifySite");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Bean(name = "variantTaskExecutor")
    public ThreadPoolTaskExecutor variantTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setMaxPoolSize(30);
        threadPoolTaskExecutor.setThreadNamePrefix("shopifyVariant");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
