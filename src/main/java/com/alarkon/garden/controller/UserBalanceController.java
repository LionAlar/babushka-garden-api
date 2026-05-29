package com.alarkon.garden.controller;

import com.alarkon.garden.model.User;
import com.alarkon.garden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserBalanceController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users/{userId}/balance")
    public String getBalance(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return "Баланс бабушки " + user.getUsername() + ": " + user.getBalance() + " монет";
    }

    @PostMapping("/api/users/{userId}/add-money")
    public String addMoney(@PathVariable Long userId, @RequestParam int amount) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
        return "Добавлено " + amount + " монет. Новый баланс: " + user.getBalance();
    }
}