package com.apex.pos.service;

import com.apex.pos.dto.CheckoutItemRequest;
import com.apex.pos.dto.CheckoutRequest;
import com.apex.pos.model.Product;
import com.apex.pos.model.Transaction;
import com.apex.pos.model.TransactionItem;
import com.apex.pos.model.User;
import com.apex.pos.repository.ProductRepository;
import com.apex.pos.repository.TransactionItemRepository;
import com.apex.pos.repository.TransactionRepository;
import com.apex.pos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionItemRepository transactionItemRepository;

    @Transactional
    public void resetDatabase() {
        // Clear existing data
        transactionItemRepository.deleteAll();
        transactionRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();

        // 1. Seed Users
        userRepository.save(new User("admin", "password", "admin", "System Admin"));
        userRepository.save(new User("cashier", "password", "cashier", "John Doe (Cashier)"));
        userRepository.save(new User("jane", "password", "cashier", "Jane Smith"));

        // 2. Seed Products
        List<Product> products = List.of(
            new Product("p1", "50102030401", "Fresh Milk 1L", 3.5, "Dairy", 45, "bottle"),
            new Product("p2", "50102030402", "Whole Grain Bread 500g", 2.2, "Bakery", 20, "loaf"),
            new Product("p3", "50102030403", "Organic Eggs 12pk", 4.8, "Dairy", 12, "pack"),
            new Product("p4", "50102030404", "Coca Cola 1.5L", 1.8, "Beverages", 80, "bottle"),
            new Product("p5", "50102030405", "Basmati Rice 5kg", 12.5, "Pantry", 35, "bag"),
            new Product("p6", "50102030406", "Spaghetti Pasta 500g", 1.1, "Pantry", 50, "pack"),
            new Product("p7", "50102030407", "Tomato Ketchup 500ml", 2.7, "Pantry", 3, "bottle"),
            new Product("p8", "50102030408", "Apples Gala (1kg)", 3.9, "Produce", 60, "bag"),
            new Product("p9", "50102030409", "Bananas Cavendish (1kg)", 2.5, "Produce", 55, "bag"),
            new Product("p10", "50102030410", "Detergent Liquid 2L", 9.8, "Household", 15, "bottle"),
            new Product("p11", "50102030411", "Potato Chips 150g", 1.9, "Snacks", 2, "pack"),
            new Product("p12", "50102030412", "Chocolate Bar 100g", 2.1, "Snacks", 0, "pack")
        );
        productRepository.saveAll(products);

        // 3. Seed Transactions
        Instant now = Instant.now();

        // TX-1001: 4 hours ago
        Transaction tx1 = new Transaction();
        tx1.setId("TX-1001");
        tx1.setCashierName("John Doe (Cashier)");
        tx1.setPaymentMethod("Cash");
        tx1.setSubtotal(9.2);
        tx1.setDiscount(0.0);
        tx1.setTax(0.74);
        tx1.setTotal(9.94);
        tx1.setCreatedAt(now.minus(4, ChronoUnit.HOURS).toString());
        List<TransactionItem> items1 = List.of(
            new TransactionItem("p1", "Fresh Milk 1L", 3.5, 2, 7.0),
            new TransactionItem("p2", "Whole Grain Bread 500g", 2.2, 1, 2.2)
        );
        tx1.getItems().addAll(items1);
        transactionRepository.save(tx1);

        // TX-1002: 2.5 hours ago
        Transaction tx2 = new Transaction();
        tx2.setId("TX-1002");
        tx2.setCashierName("John Doe (Cashier)");
        tx2.setPaymentMethod("Card");
        tx2.setSubtotal(25.7);
        tx2.setDiscount(2.0);
        tx2.setTax(2.06);
        tx2.setTotal(25.76);
        tx2.setCreatedAt(now.minus(2, ChronoUnit.HOURS).minus(30, ChronoUnit.MINUTES).toString());
        List<TransactionItem> items2 = List.of(
            new TransactionItem("p5", "Basmati Rice 5kg", 12.5, 1, 12.5),
            new TransactionItem("p8", "Apples Gala (1kg)", 3.9, 2, 7.8),
            new TransactionItem("p4", "Coca Cola 1.5L", 1.8, 3, 5.4)
        );
        tx2.getItems().addAll(items2);
        transactionRepository.save(tx2);

        // TX-1003: 50 minutes ago
        Transaction tx3 = new Transaction();
        tx3.setId("TX-1003");
        tx3.setCashierName("Jane Smith");
        tx3.setPaymentMethod("QR");
        tx3.setSubtotal(14.2);
        tx3.setDiscount(0.0);
        tx3.setTax(1.14);
        tx3.setTotal(15.34);
        tx3.setCreatedAt(now.minus(50, ChronoUnit.MINUTES).toString());
        List<TransactionItem> items3 = List.of(
            new TransactionItem("p10", "Detergent Liquid 2L", 9.8, 1, 9.8),
            new TransactionItem("p6", "Spaghetti Pasta 500g", 1.1, 4, 4.4)
        );
        tx3.getItems().addAll(items3);
        transactionRepository.save(tx3);
    }

    @Transactional
    public Transaction checkout(CheckoutRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        List<TransactionItem> itemsToSave = new ArrayList<>();
        double subtotal = 0;

        for (CheckoutItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + itemReq.getProductId()));

            if (product.getStock() < itemReq.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock for " + product.getName());
            }

            // Deduct stock
            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepository.save(product);

            // Create TransactionItem
            double itemTotal = product.getPrice() * itemReq.getQuantity();
            subtotal += itemTotal;

            TransactionItem txItem = new TransactionItem(
                product.getId(),
                product.getName(),
                product.getPrice(),
                itemReq.getQuantity(),
                Math.round(itemTotal * 100.0) / 100.0
            );
            itemsToSave.add(txItem);
        }

        double discountAmount = subtotal * (request.getDiscountPercent() / 100.0);
        double taxableAmount = subtotal - discountAmount;
        double tax = taxableAmount * request.getTaxRate();
        double total = taxableAmount + tax;

        // Generate Transaction ID: TX-100X
        // Get the number of transactions to generate unique contiguous IDs
        long txCount = transactionRepository.count();
        String txId = "TX-" + (1001 + txCount);

        // Ensure uniqueness just in case
        while (transactionRepository.existsById(txId)) {
            txCount++;
            txId = "TX-" + (1001 + txCount);
        }

        Transaction transaction = new Transaction();
        transaction.setId(txId);
        transaction.setCashierName(request.getCashierName());
        transaction.setPaymentMethod(request.getPaymentMethod());
        transaction.setSubtotal(Math.round(subtotal * 100.0) / 100.0);
        transaction.setDiscount(Math.round(discountAmount * 100.0) / 100.0);
        transaction.setTax(Math.round(tax * 100.0) / 100.0);
        transaction.setTotal(Math.round(total * 100.0) / 100.0);
        transaction.setCreatedAt(Instant.now().toString());
        transaction.getItems().addAll(itemsToSave);

        return transactionRepository.save(transaction);
    }
}
