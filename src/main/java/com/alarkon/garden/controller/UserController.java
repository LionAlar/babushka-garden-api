package com.alarkon.garden.controller;

import com.alarkon.garden.model.User;
import com.alarkon.garden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        return userRepository.save(user);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "Пользователь удален";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Такой пользователь уже есть!";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setBalance(1000);
        user.setBargainSkill(0);
        userRepository.save(user);
        return "Пользователь " + username + " зарегистрирован!";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return "Пользователь не найден";
        }
        if (!user.getPassword().equals(password)) {
            return "Неверный пароль";
        }
        return "Добро пожаловать, " + username + "! Твой ID: " + user.getId();
    }
}
