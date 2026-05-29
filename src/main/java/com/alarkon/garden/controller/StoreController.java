package com.alarkon.garden.controller;

import com.alarkon.garden.model.Store;
import com.alarkon.garden.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    private StoreRepository storeRepository;

    @GetMapping
    public List<Store> getAllStores(){
        return storeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Store getStoreById(@PathVariable Long id){
        return storeRepository.findById(id).orElseThrow();
    }

    @GetMapping("/search")
    public List<Store> searchByLocation(@RequestParam String location) {
        return storeRepository.findByLocationContaining(location);
    }

    @GetMapping("/top")
    public List<Store> getTopRated(@RequestParam double minRating) {
        return storeRepository.findByRatingGreaterThan(minRating);
    }

    @PostMapping
    public Store createStore(@RequestBody Store store) {
        return storeRepository.save(store);
    }

    @PutMapping("/{id}")
    public Store updateStore(@PathVariable Long id, @RequestBody Store store) {
        store.setId(id);
        return storeRepository.save(store);
    }

    @DeleteMapping("/{id}")
    public String deleteStore(@PathVariable Long id) {
        storeRepository.deleteById(id);
        return "Магазин удален";
    }
}
