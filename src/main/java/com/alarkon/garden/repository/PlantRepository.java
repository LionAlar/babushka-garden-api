package com.alarkon.garden.repository;

import com.alarkon.garden.model.Plant;
import com.alarkon.garden.model.PlantStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByOwnerId(Long ownerId);
    List<Plant> findByOwnerIdAndStatus(Long ownerId, PlantStatus status);
    List<Plant> findByStatus(PlantStatus status);
    List<Plant> findByPlantedAtBeforeAndStatus(LocalDateTime time, PlantStatus status);
    long countByOwnerIdAndStatus(Long ownerId, PlantStatus status);
}
