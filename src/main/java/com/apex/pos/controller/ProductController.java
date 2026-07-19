package com.apex.pos.controller;

import com.apex.pos.model.Product;
import com.apex.pos.model.SubProduct;
import com.apex.pos.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        if (product.getId() == null || product.getId().isEmpty()) {
            product.setId("p_" + UUID.randomUUID().toString().substring(0, 8));
        }
        if (product.getSubProducts() != null) {
            for (SubProduct sp : product.getSubProducts()) {
                if (sp.getId() == null || sp.getId().isEmpty()) {
                    sp.setId("sp_" + UUID.randomUUID().toString().substring(0, 8));
                }
            }
        }
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id));

        product.setName(productDetails.getName());
        product.setSku(productDetails.getSku());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());
        product.setStock(productDetails.getStock());
        product.setUnit(productDetails.getUnit());

        product.getSubProducts().clear();
        if (productDetails.getSubProducts() != null) {
            for (SubProduct sp : productDetails.getSubProducts()) {
                if (sp.getId() == null || sp.getId().isEmpty()) {
                    sp.setId("sp_" + UUID.randomUUID().toString().substring(0, 8));
                }
                product.getSubProducts().add(sp);
            }
        }

        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + id));
        productRepository.delete(product);
    }
}
