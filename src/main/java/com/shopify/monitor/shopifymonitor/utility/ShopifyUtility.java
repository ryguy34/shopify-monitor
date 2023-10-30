package com.shopify.monitor.shopifymonitor.utility;

import com.shopify.monitor.shopifymonitor.persistance.model.Variant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopifyUtility {

    public String stripSiteName(String url) {
        return url.replace("https://", "").replace(".com", "");
    }

    public Variant cleanVariantData(Variant variant) {
        if (variant.getTitle() != null) {
            String cleanedTitle = cleanTitle(variant.getTitle());
            variant.setTitle(cleanedTitle);
        }

        if (variant.getSku() != null) {
            String cleanedSku = cleanSku(variant.getSku());
            variant.setSku(cleanedSku);
        }

        return variant;
    }

    public List<Variant> cleanVariantData(List<Variant> variants) {
        for (Variant variant : variants) {
            if (variant.getTitle() != null) {
                String cleanedTitle = cleanTitle(variant.getTitle());
                variant.setTitle(cleanedTitle);
            }

            if (variant.getSku() != null) {
                String cleanedSku = cleanSku(variant.getSku());
                variant.setSku(cleanedSku);
            }
        }

        return variants;
    }

    private String cleanSku(String sku) {
        return sku.replaceAll("---.*", "");
    }

    private String cleanTitle(String title) {
        return title.replaceAll("[^a-zA-Z0-9.]", "");
    }
}
