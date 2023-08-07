package com.shopify.monitor.shopifymonitor.persistance.repository;

import com.shopify.monitor.shopifymonitor.persistance.model.ShopifyStoreInventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopifyRepository extends MongoRepository<ShopifyStoreInventory, String> {
}
