package com.shopify.monitor.shopifymonitor.persistance.model;

import com.shopify.monitor.shopifymonitor.api.vo.Image;
import com.shopify.monitor.shopifymonitor.api.vo.Option;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@Document("product")
public class Product {
    @Id
    private String id;
    private String siteName;
    private String title;
    private String handle;
    private String publishedAt;
    private String createdAt;
    private String updatedAt;
    private String vendor;
    private String productType;
    private List<String> tags;
    private List<Image> images;
    private List<Option> options;
}
