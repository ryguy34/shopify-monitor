package com.shopify.monitor.shopifymonitor.persistance.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document("variant")
public class Variant {
    @Id
    private String id;
    private String title;
    private String sku;
    private Boolean available;
    private String price;
    private String compareAtPrice;
    private Long position;
    private String productId;
    private String createdAt;
    private String updatedAt;
}
