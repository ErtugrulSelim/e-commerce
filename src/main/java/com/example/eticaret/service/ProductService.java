package com.example.eticaret.service;

import com.example.eticaret.Enum.Category;
import com.example.eticaret.dto.ProductDto;
import com.example.eticaret.model.Product;
import com.example.eticaret.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return dto;
    }

    private List<ProductDto> getAllProductDtos() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getAllProducts() {
        return getAllProductDtos();
    }

    public List<ProductDto> getCategoryProduct(Category category) {
        return getAllProducts().stream().filter(product ->
                product.getCategory().equals(category)).collect(Collectors.toList());
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        productRepository.delete(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

}
