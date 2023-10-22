package com.shopify.monitor.shopifymonitor.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyStoreInventoryVO {
    private String storeName;
    private List<ProductVO> products;
}
