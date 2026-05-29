package com.alarkon.garden.controller;

import com.alarkon.garden.model.*;
import com.alarkon.garden.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class GardenController {

    @Autowired
    private SeedPacketRepository seedPacketRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/plant/{userId}/{seedPacketId}/{quantity}")
    public String plant(
            @PathVariable Long userId,
            @PathVariable Long seedPacketId,
            @PathVariable Integer quantity) {

        User user = userRepository.findById(userId).orElseThrow();
        SeedPacket seedPacket = seedPacketRepository.findById(seedPacketId).orElseThrow();

        if (!seedPacket.getOwner().getId().equals(userId)) {
            return "Это не твой пакет семян!";
        }

        if (seedPacket.getRemainingSeeds() < quantity) {
            return "Не хватает семян! Осталось: " + seedPacket.getRemainingSeeds() +
                    ", нужно: " + quantity;
        }

        int growTimeHours = seedPacket.getProduct().getGrowTimeHours();

        for (int i = 0; i < quantity; i++) {
            Plant plant = new Plant();
            plant.setOwner(user);
            plant.setSeedPacket(seedPacket);
            plant.setStatus(PlantStatus.SEED);
            plant.setPlantedAt(LocalDateTime.now());
            plant.setMatureAt(LocalDateTime.now().plusHours(growTimeHours));
            plant.setActualSellPrice(seedPacket.getProduct().getBaseSellPrice());
            plantRepository.save(plant);
        }

        seedPacket.setRemainingSeeds(seedPacket.getRemainingSeeds() - quantity);
        seedPacketRepository.save(seedPacket);

        return "Посажено " + quantity + " растений. Осталось семян: " + seedPacket.getRemainingSeeds();
    }

    @GetMapping("/api/users/{userId}/plants")
    public List<Plant> getUserPlants(@PathVariable Long userId) {
        return plantRepository.findByOwnerId(userId);
    }

    @GetMapping("/api/users/{userId}/plants/status/{status}")
    public List<Plant> getUserPlantsByStatus(@PathVariable Long userId, @PathVariable String status) {
        return plantRepository.findByOwnerIdAndStatus(userId, PlantStatus.valueOf(status));
    }
}