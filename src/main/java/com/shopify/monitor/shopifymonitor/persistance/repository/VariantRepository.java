package com.shopify.monitor.shopifymonitor.persistance.repository;

import com.shopify.monitor.shopifymonitor.persistance.model.Variant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VariantRepository extends MongoRepository<Variant, String> {
    List<Variant> findAllByProductId(String productId);
}
