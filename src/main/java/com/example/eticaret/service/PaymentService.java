package com.example.eticaret.service;

import com.example.eticaret.model.*;
import com.example.eticaret.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor

public class PaymentService {
    private PaymentRepository paymentRepository;
    private CardRepository cardRepository;
    private UserRepository userRepository;
    private CardItemRepository cardItemRepository;


    public void getPayRequest(Product product, int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Card card = cardRepository.findByUser(user).orElseThrow();
        List<CardItem> cardItem = cardItemRepository.findByCard(card);
        Payment payment = paymentRepository.findByCard(card);

        if(!payment.isRequest()
                && cardItem.get(0).getProduct().equals(product)
                && cardItem.get(0).getQuantity() > quantity) {
            payment.setRequest(true);
            paymentRepository.save(payment);
        }
        else {
            throw new RuntimeException("There is no product that name or much quantity");
        }
    }
    public void getPaySuccess(Payment payment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        Card card = cardRepository.findByUser(user).orElseThrow();
        List<CardItem> cardItem = cardItemRepository.findByCard(card);



    }

}
