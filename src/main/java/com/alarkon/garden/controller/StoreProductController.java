package com.alarkon.garden.controller;

import com.alarkon.garden.model.StoreProduct;
import com.alarkon.garden.repository.StoreProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store-products")
public class StoreProductController {
    @Autowired
    private StoreProductRepository storeProductRepository;

    @GetMapping
    public List<StoreProduct> getAllStoreProducts(){
        return storeProductRepository.findAll();
    }

    @GetMapping("/{id}")
    public StoreProduct getStoreProductById(@PathVariable Long id) {
        return storeProductRepository.findById(id).orElseThrow();
    }

    @GetMapping("/store/{storeId}")
    public List<StoreProduct> getByStoreId(@PathVariable Long storeId){
        return storeProductRepository.findByStoreId(storeId);
    }

    @GetMapping("/product/{productId}")
    public List<StoreProduct> getByProductId(@PathVariable Long productId) {
        return storeProductRepository.findByProductId(productId);
    }

    @GetMapping("/check")
    public List<StoreProduct> checkStoreProduct(@RequestParam Long storeId, @RequestParam Long productId) {
        return storeProductRepository.findByStoreIdAndProductId(storeId, productId);
    }

    @PostMapping
    public StoreProduct createStoreProduct(@RequestBody StoreProduct storeProduct) {
        return storeProductRepository.save(storeProduct);
    }

    @PutMapping("/{id}")
    public StoreProduct updateStoreProduct(@PathVariable Long id, @RequestBody StoreProduct storeProduct) {
        storeProduct.setId(id);
        return storeProductRepository.save(storeProduct);
    }

    @DeleteMapping("/{id}")
    public String deleteStoreProduct(@PathVariable Long id) {
        storeProductRepository.deleteById(id);
        return "Связь магазин-товар удалена";
    }
}
