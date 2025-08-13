package com.example.eticaret.service;

import com.example.eticaret.Enum.Category;
import com.example.eticaret.dto.ProductDto;
import com.example.eticaret.exceptions.AlreadyExistException;
import com.example.eticaret.exceptions.NotFoundException;
import com.example.eticaret.model.Product;
import com.example.eticaret.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor

public class ProductService {

    private ProductRepository productRepository;

    public ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setAverageRating(product.getAverageRating());
        return dto;
    }

    private List<ProductDto> getAllProductDto() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getAllProducts() {
        if (getAllProductDto().isEmpty()) {
            throw new NotFoundException("No products found");
        }
        return getAllProductDto();
    }

    public List<ProductDto> getCategoryProduct(Category category) {
        if (category == null) {
            throw new NotFoundException("No category found");
        }
        return getAllProducts().stream().filter(product ->
                product.getCategory().equals(category)).collect(Collectors.toList());
    }

    public ResponseEntity<String> addProduct(Product product) {
        Optional<Product> newProduct = productRepository.findById(product.getId());
        if (newProduct.isPresent()) {
            throw new AlreadyExistException("This product already exists");
        }
        productRepository.save(product);
        return ResponseEntity.ok("Product added successfully");
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
    }

    public ResponseEntity<String> updateProduct(Product product) {
        Optional<Product> newProduct = productRepository.findById(product.getId());
        if (newProduct.isPresent()) {
            product.setStock(newProduct.get().getStock() + product.getStock());
            productRepository.save(product);
            return ResponseEntity.ok("Product updated successfully");
        } else {
            throw new NotFoundException("Product not found");
        }
    }

}
