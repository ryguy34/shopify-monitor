package com.shopify.monitor.shopifymonitor.persistance.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@Document("store")
public class ShopifyStoreInventory {
    @Id
    private String storeName;
    private List<Product> products;
}
