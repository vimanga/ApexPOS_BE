package com.apex.pos.dto;

public class RestockOrderItemRequest {
    private String productId;
    private String subProductId;
    private int quantityAdded;

    public RestockOrderItemRequest() {
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

    public int getQuantityAdded() {
        return quantityAdded;
    }

    public void setQuantityAdded(int quantityAdded) {
        this.quantityAdded = quantityAdded;
    }
}
