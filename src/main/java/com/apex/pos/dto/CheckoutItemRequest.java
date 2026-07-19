package com.apex.pos.dto;

public class CheckoutItemRequest {
    private String productId;
    private String subProductId;
    private int quantity;
    private double discountPercent;

    public CheckoutItemRequest() {
    }

    public CheckoutItemRequest(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public CheckoutItemRequest(String productId, String subProductId, int quantity, double discountPercent) {
        this.productId = productId;
        this.subProductId = subProductId;
        this.quantity = quantity;
        this.discountPercent = discountPercent;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
}
