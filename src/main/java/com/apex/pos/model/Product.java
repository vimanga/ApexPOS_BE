package com.apex.pos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    private double price;
    private String category;
    private int stock;
    private int reorderLevel = 5;
    private String unit;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private List<SubProduct> subProducts = new ArrayList<>();

    public Product() {
    }

    public Product(String id, String sku, String name, double price, String category, int stock, String unit) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.reorderLevel = 5;
        this.unit = unit;
    }

    public Product(String id, String sku, String name, double price, String category, int stock, String unit, List<SubProduct> subProducts) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.reorderLevel = 5;
        this.unit = unit;
        this.subProducts = subProducts != null ? subProducts : new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<SubProduct> getSubProducts() {
        return subProducts;
    }

    public void setSubProducts(List<SubProduct> subProducts) {
        this.subProducts = subProducts;
    }
}
