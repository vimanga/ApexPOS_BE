package com.apex.pos.controller;

import com.apex.pos.dto.RestockOrderRequest;
import com.apex.pos.model.RestockOrder;
import com.apex.pos.service.RestockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restock")
public class RestockController {

    @Autowired
    private RestockService restockService;

    @GetMapping
    public List<RestockOrder> getAllRestockOrders() {
        return restockService.getAllRestockOrders();
    }

    @PostMapping
    public RestockOrder createRestockOrder(@RequestBody RestockOrderRequest request) {
        return restockService.createRestockOrder(request);
    }
}
