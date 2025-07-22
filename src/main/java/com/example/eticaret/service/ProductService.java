package com.example.eticaret.service;

import com.example.eticaret.model.User;
import com.example.eticaret.model.Product;
import com.example.eticaret.repository.UserRepository;
import com.example.eticaret.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class ProductService {

    private ProductRepository productRepository;
    private UserRepository userRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    public Product addProduct(Product product,Long userId) {
        User user =userRepository.findById(userId).orElse(null);

       if(!user.isAdmin()) {
           return null;
       }
        return productRepository.save(product);
    }
    public void deleteProduct(Long productId,Long userId) {
        User user =userRepository.findById(userId).orElse(null);

        if(!user.isAdmin()){
            return;
        }
            Product product = productRepository.findById(productId).orElse(null);
            productRepository.delete(product);
        }
}
