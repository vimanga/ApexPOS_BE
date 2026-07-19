package com.apex.pos.controller;

import com.apex.pos.dto.CheckoutRequest;
import com.apex.pos.model.Transaction;
import com.apex.pos.repository.TransactionRepository;
import com.apex.pos.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private StoreService storeService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllByOrderByIdDesc();
    }

    @PostMapping
    public Transaction checkout(@RequestBody CheckoutRequest request) {
        return storeService.checkout(request);
    }
}
