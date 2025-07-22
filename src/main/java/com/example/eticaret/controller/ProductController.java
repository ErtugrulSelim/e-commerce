package com.example.eticaret.controller;

import com.example.eticaret.model.Product;
import com.example.eticaret.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        Product saved = productService.addProduct(product);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/deleteProduct{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @RequestParam Long userId) {
        productService.deleteProduct(id, userId);
        return ResponseEntity.noContent().build();
    }
}

