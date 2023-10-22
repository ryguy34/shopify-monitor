package com.shopify.monitor.shopifymonitor.scheduler;

import com.shopify.monitor.shopifymonitor.service.RetrieveProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SiteMonitorScheduler {

    @Autowired
    private RetrieveProducts retrieveProducts;
    private Map<String, String> siteUrlMap = new HashMap<>();

    // TODO: need to read app.yml site urls and call retrieveProducts for each

    // TODO: make this method a cron job
    public void monitorSite() {
        boolean isFirstRun = true;

        if(isFirstRun) {
            isFirstRun = false;

        } else {
            // TODO: not first run so check for updates
        }
    }
}
