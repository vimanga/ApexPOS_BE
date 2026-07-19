package com.apex.pos.service;

import com.apex.pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // Seed database if empty
        if (productRepository.count() == 0) {
            System.out.println("No products found in DB. Seeding initial store data...");
            storeService.resetDatabase();
            System.out.println("Initial store data seeded successfully!");
        } else {
            System.out.println("Products found in DB. Skipping initial data seeding.");
        }
    }
}
