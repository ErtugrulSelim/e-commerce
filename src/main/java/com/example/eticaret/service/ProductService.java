package com.example.eticaret.service;

import com.example.eticaret.dto.ProductDto;
import com.example.eticaret.model.User;
import com.example.eticaret.model.Product;
import com.example.eticaret.repository.UserRepository;
import com.example.eticaret.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor

public class ProductService {

    private ProductRepository productRepository;
    private UserRepository userRepository;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product ->{
            ProductDto dto = new ProductDto();
                dto.setId(String.valueOf(product.getId()));
                dto.setName(product.getName());
                dto.setPrice(product.getPrice());
                return dto;
        }).collect(Collectors.toList());
    }
    public Product findById(Long id) {

        return productRepository.findById(id).orElse(null);
    }
    public Product addProduct(Product product) {

        return productRepository.save(product);
    }
    public void deleteProduct(Long productId) {
            Product product = productRepository.findById(productId).orElse(null);
            productRepository.delete(product);
    }
    public Product updateProduct(Product product) {
      return  productRepository.save(product);
    }
}
