package com.apex.pos.controller;

import com.apex.pos.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private StoreService storeService;

    @PostMapping("/reset")
    public ResponseEntity<?> resetDatabase() {
        storeService.resetDatabase();
        return ResponseEntity.ok(Map.of("success", true, "message", "Database reset to initial demo state successfully"));
    }
}
