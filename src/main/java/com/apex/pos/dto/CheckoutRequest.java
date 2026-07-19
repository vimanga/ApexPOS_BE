package com.apex.pos.dto;

import java.util.List;

public class CheckoutRequest {
    private String cashierName;
    private String paymentMethod;
    private double discountPercent;
    private double taxRate;
    private List<CheckoutItemRequest> items;

    public CheckoutRequest() {
    }

    public CheckoutRequest(String cashierName, String paymentMethod, double discountPercent, double taxRate, List<CheckoutItemRequest> items) {
        this.cashierName = cashierName;
        this.paymentMethod = paymentMethod;
        this.discountPercent = discountPercent;
        this.taxRate = taxRate;
        this.items = items;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public List<CheckoutItemRequest> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItemRequest> items) {
        this.items = items;
    }
}
