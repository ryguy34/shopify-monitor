package com.shopify.monitor.shopifymonitor.scheduler;

import com.shopify.monitor.shopifymonitor.service.UpdateProducts;
import com.shopify.monitor.shopifymonitor.utility.SiteUrls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

@Slf4j
@Component
public class SiteMonitorScheduler {

    @Autowired
    private SiteUrls siteUrls;

    @Autowired
    private UpdateProducts updateProducts;

    private boolean isFirstRun = true;

    @Scheduled(fixedDelay = 60000)
    public void monitorSite() {
        List<Object> siteList = siteUrls.getUrls();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (Object siteUrl : siteList) {
            updateProducts.updateProducts(String.valueOf(siteUrl), isFirstRun);
        }

        isFirstRun = false;
        stopWatch.stop();
        log.info("Done updating sites in {}s", stopWatch.getTotalTimeSeconds());
    }
}
