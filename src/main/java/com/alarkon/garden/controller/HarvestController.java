package com.alarkon.garden.controller;

import com.alarkon.garden.model.*;
import com.alarkon.garden.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class HarvestController {

    @Autowired
    private PlantRepository plantRepository;

    @PostMapping("/api/harvest/{plantId}")
    public String harvest(@PathVariable Long plantId) {

        Plant plant = plantRepository.findById(plantId).orElseThrow();

        if (!plant.getStatus().equals(PlantStatus.MATURE)) {
            return "Растение еще не созрело! Статус: " + plant.getStatus();
        }

        plant.setStatus(PlantStatus.HARVESTED);
        plant.setHarvestedAt(LocalDateTime.now());
        plantRepository.save(plant);

        return "Урожай собран! Растение готово к продаже.";
    }

    @PostMapping("/api/harvest/user/{userId}")
    public String harvestAllMature(@PathVariable Long userId) {
        List<Plant> plants = plantRepository.findByOwnerIdAndStatus(userId, PlantStatus.MATURE);
        int harvested = 0;

        for (Plant plant : plants) {
            plant.setStatus(PlantStatus.HARVESTED);
            plant.setHarvestedAt(LocalDateTime.now());
            plantRepository.save(plant);
            harvested++;
        }

        return "Собрано " + harvested + " растений у пользователя " + userId;
    }
}