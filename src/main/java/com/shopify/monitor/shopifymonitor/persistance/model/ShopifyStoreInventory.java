package com.shopify.monitor.shopifymonitor.persistance.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ShopifyStoreInventory {
    private List<Product> products;
}
