package com.shopify.monitor.shopifymonitor.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class VariantVO {

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
