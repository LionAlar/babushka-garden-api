package com.alarkon.garden.controller;

import com.alarkon.garden.model.User;
import com.alarkon.garden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/forbes")
public class ForbesController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getForbes() {
        return userRepository.findAllByOrderByBalanceDesc();
    }

    @GetMapping("/top/{limit}")
    public List<User> getTopForbes(@PathVariable int limit) {
        return userRepository.findAllByOrderByBalanceDesc().stream()
                .limit(limit)
                .toList();
    }
}