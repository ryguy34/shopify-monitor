package com.shopify.monitor.shopifymonitor.scheduler;

import com.shopify.monitor.shopifymonitor.service.UpdateProductService;
import com.shopify.monitor.shopifymonitor.utility.SiteUrls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class SiteMonitorScheduler {

    @Autowired
    private SiteUrls siteUrls;

    @Autowired
    private UpdateProductService updateProductService;

    private boolean isFirstRun = true;

    @Scheduled(fixedDelay = 60000)
    public void monitorSite() {
        List<Object> siteList = siteUrls.getUrls();
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (Object siteUrl : siteList) {
            final CompletableFuture<Void> completableFuture = updateProductService.updateProducts(String.valueOf(siteUrl), isFirstRun);
            futures.add(completableFuture);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        isFirstRun = false;
        stopWatch.stop();
        log.info("Done updating sites in {}s", stopWatch.getTotalTimeSeconds());
    }
}
