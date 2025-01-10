package com.techie.microservices.inventory.repository;

import com.techie.microservices.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode,Integer quantity);
    Optional<Inventory> findBySkuCode(String skuCode);
}
