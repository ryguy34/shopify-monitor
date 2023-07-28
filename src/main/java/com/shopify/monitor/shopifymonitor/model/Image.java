package com.shopify.monitor.shopifymonitor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Image {
    private String id;
    @JsonProperty("created_at")
    private String createdAt;
    private Integer position;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("product_id")
    private Integer productId;
    @JsonProperty("variant_ids")
    private List<String> variantIds;
    private String src;
    private Integer width;
    private Integer height;
}
