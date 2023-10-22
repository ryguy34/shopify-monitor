package com.shopify.monitor.shopifymonitor.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageVO {
    private String id;
    @JsonProperty("created_at")
    private String createdAt;
    private Long position;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("product_id")
    private Long productId;
    @JsonProperty("variant_ids")
    private List<String> variantIds;
    private String src;
    private Long width;
    private Long height;
}
