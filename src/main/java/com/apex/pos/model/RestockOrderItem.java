package com.apex.pos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restock_order_items")
public class RestockOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    private String subProductId;
    private String productName;
    private int quantityAdded;

    public RestockOrderItem() {
    }

    public RestockOrderItem(String productId, String subProductId, String productName, int quantityAdded) {
        this.productId = productId;
        this.subProductId = subProductId;
        this.productName = productName;
        this.quantityAdded = quantityAdded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSubProductId() {
        return subProductId;
    }

    public void setSubProductId(String subProductId) {
        this.subProductId = subProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantityAdded() {
        return quantityAdded;
    }

    public void setQuantityAdded(int quantityAdded) {
        this.quantityAdded = quantityAdded;
    }
}
