package com.alarkon.garden.controller;

import com.alarkon.garden.model.*;
import com.alarkon.garden.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class GrowController {

    @Autowired
    private PlantRepository plantRepository;

    @PostMapping("/api/grow")
    public String grow() {

        List<Plant> plants = plantRepository.findByStatus(PlantStatus.SEED);
        List<Plant> sprouts = plantRepository.findByStatus(PlantStatus.SPROUT);
        plants.addAll(sprouts);

        int updated = 0;

        for (Plant plant : plants) {
            if (plant.getMatureAt() != null && LocalDateTime.now().isAfter(plant.getMatureAt())) {
                plant.setStatus(PlantStatus.MATURE);
                plantRepository.save(plant);
                updated++;
            }
        }

        return "Обновлено " + updated + " растений до статуса выросшие";
    }

    @PostMapping("/api/grow/user/{userId}")
    public String growUserPlants(@PathVariable Long userId) {
        List<Plant> plants = plantRepository.findByOwnerIdAndStatus(userId, PlantStatus.SEED);
        plants.addAll(plantRepository.findByOwnerIdAndStatus(userId, PlantStatus.SPROUT));

        int updated = 0;

        for (Plant plant : plants) {
            if (plant.getMatureAt() != null && LocalDateTime.now().isAfter(plant.getMatureAt())) {
                plant.setStatus(PlantStatus.MATURE);
                plantRepository.save(plant);
                updated++;
            }
        }

        return "Обновлено " + updated + " растений пользователя " + userId;
    }
}