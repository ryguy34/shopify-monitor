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
        String variantTitle = variant.getTitle();
        variantTitle = variantTitle.replaceAll("[^a-zA-Z0-9.]", "");
        variant.setTitle(variantTitle);

        String variantSku = variant.getSku();
        variantSku = variantSku.replace("---.*", "");
        variant.setSku(variantSku);

        return variant;
    }

    public List<Variant> cleanVariantData(List<Variant> variants) {
        for (Variant variant : variants) {
            String variantTitle = variant.getTitle();
            variantTitle = variantTitle.replaceAll("[^a-zA-Z0-9.]", "");
            variant.setTitle(variantTitle);

            String variantSku = variant.getSku();
            String cleanedSku = variantSku.replaceAll("---.*", "");
            variant.setSku(cleanedSku);
        }

        return variants;
    }
}
