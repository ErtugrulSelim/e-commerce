package com.example.eticaret.controller;

import com.example.eticaret.model.Order;
import com.example.eticaret.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    @PostMapping("/{userId}/{productId}")
        public ResponseEntity<String> placeOrder(@PathVariable long userId,@PathVariable long productId) {
        String saved = orderService.createOrder(userId,productId);
        return ResponseEntity.ok(saved.toString());
    }

    @DeleteMapping("/{userId}/{productId}")
        public ResponseEntity<String> deleteOrder(@PathVariable long userId,@PathVariable long productId) {
        orderService.deleteOrder(userId,productId);
        return ResponseEntity.ok("Order deleted successfully");
    }
}

