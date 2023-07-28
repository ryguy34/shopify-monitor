package com.shopify.monitor.shopifymonitor.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Option {
    private String name;
    private Integer position;
    private List<String> values;
}
