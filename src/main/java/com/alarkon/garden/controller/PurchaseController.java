package com.alarkon.garden.controller;

import com.alarkon.garden.model.SeedPacket;
import com.alarkon.garden.model.StoreProduct;
import com.alarkon.garden.model.User;
import com.alarkon.garden.repository.SeedPacketRepository;
import com.alarkon.garden.repository.StoreProductRepository;
import com.alarkon.garden.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreProductRepository storeProductRepository;

    @Autowired
    private SeedPacketRepository seedPacketRepository;

    @PostMapping("/api/purchase/{userId}/{storeProductId}/{quantity}")
    public String purchase(
            @PathVariable Long userId,
            @PathVariable Long storeProductId,
            @PathVariable Integer quantity) {

        User user = userRepository.findById(userId).orElseThrow();
        StoreProduct storeProduct = storeProductRepository.findById(storeProductId).orElseThrow();

        int totalPrice = storeProduct.getPurchasePrice() * quantity;

        if (user.getBalance() < totalPrice) {
            return "Не хватает денег! Нужно: " + totalPrice + ", есть: " + user.getBalance();
        }

        if (storeProduct.getInStock() < quantity) {
            return "В магазине нет столько товара! В наличии: " + storeProduct.getInStock();
        }

        user.setBalance(user.getBalance() - totalPrice);
        storeProduct.setInStock(storeProduct.getInStock() - quantity);

        for (int i = 0; i < quantity; i++) {
            SeedPacket seedPacket = new SeedPacket();
            seedPacket.setOwner(user);
            seedPacket.setStore(storeProduct.getStore());
            seedPacket.setProduct(storeProduct.getProduct());
            seedPacket.setPurchasePrice(storeProduct.getPurchasePrice());
            seedPacket.setRemainingSeeds(storeProduct.getProduct().getSeedsPerPack());
            seedPacketRepository.save(seedPacket);
        }

        userRepository.save(user);
        storeProductRepository.save(storeProduct);

        return "Куплено " + quantity + " пакетов семян " + storeProduct.getProduct().getName() +
                " за " + totalPrice + " монет. Остаток: " + user.getBalance();
    }
}
