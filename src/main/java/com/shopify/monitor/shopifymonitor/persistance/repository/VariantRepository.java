package com.shopify.monitor.shopifymonitor.persistance.repository;

import com.shopify.monitor.shopifymonitor.persistance.model.Variant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VariantRepository extends MongoRepository<Variant, String> {
}
