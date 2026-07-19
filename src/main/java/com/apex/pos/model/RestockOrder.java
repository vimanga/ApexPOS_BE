package com.apex.pos.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restock_orders")
public class RestockOrder {

    @Id
    private String id;

    private String salespersonName;
    private String supplierName;
    private String orderDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "restock_order_id")
    private List<RestockOrderItem> items = new ArrayList<>();

    public RestockOrder() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<RestockOrderItem> getItems() {
        return items;
    }

    public void setItems(List<RestockOrderItem> items) {
        this.items = items;
    }
}
