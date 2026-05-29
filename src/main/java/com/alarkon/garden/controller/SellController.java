package com.alarkon.garden.controller;

import com.alarkon.garden.model.*;
import com.alarkon.garden.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellController {

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/sell/{plantId}")
    public String sell(@PathVariable Long plantId) {

        Plant plant = plantRepository.findById(plantId).orElseThrow();

        if (!plant.getStatus().equals(PlantStatus.HARVESTED)) {
            return "Сначала собери урожай! Статус: " + plant.getStatus();
        }

        User user = plant.getOwner();
        int basePrice = plant.getActualSellPrice();

        int bonusPercent = user.getBargainSkill() * 5;
        int finalPrice = basePrice + (basePrice * bonusPercent / 100);

        user.setBalance(user.getBalance() + finalPrice);
        plantRepository.delete(plant);
        userRepository.save(user);

        return "Продано за " + finalPrice + " монет! (база: " + basePrice +
                ", бонус: +" + bonusPercent + "%). Баланс: " + user.getBalance();
    }

    @PostMapping("/api/sell/user/{userId}")
    public String sellAllHarvested(@PathVariable Long userId) {
        List<Plant> plants = plantRepository.findByOwnerIdAndStatus(userId, PlantStatus.HARVESTED);
        User user = userRepository.findById(userId).orElseThrow();
        int totalEarned = 0;

        for (Plant plant : plants) {
            int basePrice = plant.getActualSellPrice();
            int bonusPercent = user.getBargainSkill() * 5;
            int finalPrice = basePrice + (basePrice * bonusPercent / 100);
            totalEarned += finalPrice;
            plantRepository.delete(plant);
        }

        user.setBalance(user.getBalance() + totalEarned);
        userRepository.save(user);

        return "Продано " + plants.size() + " растений за " + totalEarned +
                " монет. Новый баланс: " + user.getBalance();
    }
}