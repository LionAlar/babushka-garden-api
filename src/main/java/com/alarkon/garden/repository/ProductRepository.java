package com.alarkon.garden.repository;

import com.alarkon.garden.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByVariety(String variety);
    List<Product> findByGrowTimeHoursLessThan(int hours);
}
