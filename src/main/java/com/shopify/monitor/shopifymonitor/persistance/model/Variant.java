package com.shopify.monitor.shopifymonitor.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Document("variant")
public class Variant {
    @Id
    private String id;
    private String title;
    private String option1;
    private String option2;
    private String option3;
    private String sku;
    @JsonProperty("requires_shipping")
    private Boolean requiresShipping;
    private Boolean taxable;
//    @JsonProperty("featured_image")
//    private String featuredImage;
    private Boolean available;
    private String price;
    private Long grams;
    @JsonProperty("compare_at_price")
    private String compareAtPrice;
    private Long position;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
}
