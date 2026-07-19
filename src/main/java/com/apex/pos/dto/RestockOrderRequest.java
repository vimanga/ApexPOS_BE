package com.apex.pos.dto;

import java.util.List;

public class RestockOrderRequest {
    private String salespersonName;
    private String supplierName;
    private List<RestockOrderItemRequest> items;

    public RestockOrderRequest() {
    }

    public String getSalespersonName() {
        return salespersonName;
    }

    public void setSalespersonName(String salespersonName) {
        this.salespersonName = salespersonName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<RestockOrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<RestockOrderItemRequest> items) {
        this.items = items;
    }
}
