package com.example.eticaret.service;

import com.example.eticaret.model.Card;
import com.example.eticaret.model.Product;
import com.example.eticaret.model.User;
import com.example.eticaret.repository.CardRepository;
import com.example.eticaret.repository.UserRepository;
import com.example.eticaret.repository.ProductRepository;
import com.example.eticaret.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CardService {

    private CardRepository cardRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private JwtUtil jwtUtil;
    public List<Card> getAllProductsfromCards() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return cardRepository.findByUserUsername(username);
    }
    public String createCard(long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Card card = cardRepository.findByUser(user).orElse(new Card());
        Product product = productRepository.findById(productId).orElseThrow();
        if(card.getUser() == null) {
            card.setUser(user);
            card.setProducts(new ArrayList<>());
            card.getProducts().add(product);
            return cardRepository.save(card).toString();
        }
        else {
            card.getProducts().add(product);
            System.out.println("kart eklendi2");
            return cardRepository.save(card).toString();
        }
    }
    public String deleteCard(long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Product product = productRepository.findById(productId).orElseThrow();
        Card card = cardRepository.findByProducts(product).orElseThrow();
        cardRepository.delete(card);
        return "Card deleted";
    }
//    public String deleteOrder(long userId, long productId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        Card order = orderRepository.findByUserAndProductsContaining(user, product)
//                .orElseThrow(() -> new RuntimeException("Card not found"));
//
//        orderRepository.delete(order);
//
//        return "Card deleted successfully";
//    }

}
