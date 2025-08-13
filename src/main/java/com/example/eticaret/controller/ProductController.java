package com.example.eticaret.controller;

import com.example.eticaret.Enum.Category;
import com.example.eticaret.dto.ProductDto;
import com.example.eticaret.model.Product;
import com.example.eticaret.service.ProductService;
import jakarta.validation.Valid;
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

    @GetMapping("/{category}")
    public List<ProductDto> getProducts(@PathVariable String category) {
        return productService.getCategoryProduct(Category.valueOf(category));
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody @Valid Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody @Valid Product product) {
        return productService.updateProduct(product);
    }
}

