package com.shopify.monitor.shopifymonitor.persistance.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Document("product")
public class Product {
    @Id
    private String id;
    private String title;
    private String handle;
    @JsonProperty("body_html")
    private String bodyHtml;
    @JsonProperty("published_at")
    private String publishedAt;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    private String vendor;
    @JsonProperty("product_type")
    private String productType;
    private List<String> tags;
    private List<Variant> variants;
    private List<Image> images;
    private List<Option> options;
}
