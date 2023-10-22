package com.shopify.monitor.shopifymonitor.persistance.repository;

import com.shopify.monitor.shopifymonitor.persistance.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
