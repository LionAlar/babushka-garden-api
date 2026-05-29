package com.alarkon.garden.controller;

import com.alarkon.garden.model.Product;
import com.alarkon.garden.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow();
    }

    @GetMapping("/variety/{variety}")
    public List<Product> getByVariety(@PathVariable String variety) {
        return productRepository.findByVariety(variety);
    }

    @GetMapping("/fast")
    public List<Product> getFastGrowing(@RequestParam int maxHours) {
        return productRepository.findByGrowTimeHoursLessThan(maxHours);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "Товар удален";
    }
}
