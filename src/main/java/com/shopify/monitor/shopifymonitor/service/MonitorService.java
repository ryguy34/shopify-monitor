package com.shopify.monitor.shopifymonitor.service;

import org.springframework.stereotype.Service;

@Service
public class MonitorService {

    public void monitorSite() {
        boolean isFirstRun = true;

        if(isFirstRun) {
            isFirstRun = false;
        } else {
            // TODO: not first run so check for updates
        }
    }
}
