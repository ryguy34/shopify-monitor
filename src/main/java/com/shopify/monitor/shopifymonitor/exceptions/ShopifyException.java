package com.shopify.monitor.shopifymonitor.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ShopifyException {
    String status;
    String message;
}
