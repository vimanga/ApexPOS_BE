package com.apex.pos.service;

import com.apex.pos.dto.RestockOrderItemRequest;
import com.apex.pos.dto.RestockOrderRequest;
import com.apex.pos.model.Product;
import com.apex.pos.model.RestockOrder;
import com.apex.pos.model.RestockOrderItem;
import com.apex.pos.model.SubProduct;
import com.apex.pos.repository.ProductRepository;
import com.apex.pos.repository.RestockOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestockService {

    @Autowired
    private RestockOrderRepository restockOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<RestockOrder> getAllRestockOrders() {
        return restockOrderRepository.findAll();
    }

    @Transactional
    public RestockOrder createRestockOrder(RestockOrderRequest request) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restock order must contain items.");
        }

        RestockOrder order = new RestockOrder();
        
        long orderCount = restockOrderRepository.count();
        String orderId = "RO-" + (1001 + orderCount);
        while (restockOrderRepository.existsById(orderId)) {
            orderCount++;
            orderId = "RO-" + (1001 + orderCount);
        }

        order.setId(orderId);
        order.setSalespersonName(request.getSalespersonName());
        order.setSupplierName(request.getSupplierName());
        order.setOrderDate(Instant.now().toString());

        List<RestockOrderItem> items = new ArrayList<>();

        for (RestockOrderItemRequest itemReq : request.getItems()) {
            if (itemReq.getQuantityAdded() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be positive.");
            }

            Product product = productRepository.findById(itemReq.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: " + itemReq.getProductId()));

            String productName = "";

            if (itemReq.getSubProductId() != null && !itemReq.getSubProductId().isEmpty()) {
                SubProduct subProduct = product.getSubProducts().stream()
                    .filter(sp -> sp.getId().equals(itemReq.getSubProductId()))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subproduct not found: " + itemReq.getSubProductId()));

                subProduct.setStock(subProduct.getStock() + itemReq.getQuantityAdded());
                productName = product.getName() + " - " + subProduct.getName();
            } else {
                product.setStock(product.getStock() + itemReq.getQuantityAdded());
                productName = product.getName();
            }

            productRepository.save(product);

            RestockOrderItem orderItem = new RestockOrderItem(
                itemReq.getProductId(),
                itemReq.getSubProductId(),
                productName,
                itemReq.getQuantityAdded()
            );
            items.add(orderItem);
        }

        order.getItems().addAll(items);
        return restockOrderRepository.save(order);
    }
}
