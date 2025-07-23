package com.example.eticaret.service;

import com.example.eticaret.model.Product;
import com.example.eticaret.model.User;
import com.example.eticaret.model.Order;
import com.example.eticaret.repository.OrderRepository;
import com.example.eticaret.repository.UserRepository;
import com.example.eticaret.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Order save(Order order) {
        return orderRepository.save(order);
    }
    public Order findById(long id) {
        return orderRepository.findById(id).orElse(null);
    }
    public String createOrder(long userId, long productId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return "User not found";
        }
        List<Product> products = productRepository.findAllById(productId);
        if(products.isEmpty()) {
            return "Product not found";
        }
        Order order = new Order();
        order.setUser(userOpt.get());
        order.setProducts(products);
        return orderRepository.save(order).toString();
    }
    public String deleteOrder(long userId, long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Order order = orderRepository.findByUserAndProductsContaining(user, product)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderRepository.delete(order);

        return "Order deleted successfully";
    }

}
