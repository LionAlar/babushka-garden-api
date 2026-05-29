package com.alarkon.garden.repository;

import com.alarkon.garden.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByLocationContaining(String location);
    List<Store> findByRatingGreaterThan(double rating);
}
