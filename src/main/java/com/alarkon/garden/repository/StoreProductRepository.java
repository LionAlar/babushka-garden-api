package com.alarkon.garden.repository;

import com.alarkon.garden.model.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {
    List<StoreProduct> findByStoreId(Long storeId);
    List<StoreProduct> findByProductId(Long productId);
    List<StoreProduct> findByStoreIdAndProductId(Long storeId, Long productId);
}
