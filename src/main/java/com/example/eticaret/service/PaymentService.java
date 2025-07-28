package com.example.eticaret.service;

import com.example.eticaret.dto.PaymentDto;
import com.example.eticaret.model.*;
import com.example.eticaret.repository.*;
import com.example.eticaret.dto.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PaymentService {
    private PaymentRepository paymentRepository;
    private CardRepository cardRepository;
    private UserRepository userRepository;
    private CardItemRepository cardItemRepository;
    private ProductRepository productRepository;

    public List<PaymentDto> getPastPayments() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Card card = cardRepository.findByUser(user).orElseThrow();
        List<Payment> payments = paymentRepository.findByCard(card);
        return payments.stream().map(payment-> {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setSuccess(payment.isSuccess());
            paymentDto.setProduct(payment.getProduct());
            paymentDto.setQuantity(payment.getQuantity());
            paymentDto.setCard(payment.getCard());
            return  paymentDto;
        }).collect(Collectors.toList());
    }
    public void getPayProduct(long productId, int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Card card = cardRepository.findByUser(user).orElseThrow();
        List<CardItem> cardItem = cardItemRepository.findByCard(card);
        Product product = productRepository.findById(productId).orElseThrow();
        Payment payment = new Payment();

        payment.setProduct(product);
        payment.setQuantity(quantity);
        payment.setCard(card);
        if(cardItem.get(0).getQuantity() >= quantity && product.getStock() >= quantity) {
            cardItem.get(0).setQuantity(cardItem.get(0).getQuantity() - quantity);
            product.setStock(product.getStock() - quantity);
            payment.setSuccess(true);
            paymentRepository.save(payment);
        }
        else if (cardItem.get(0).getQuantity() < quantity) {
            throw new RuntimeException("Not enough product at card");
        }
        else if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough product at Stock");
        }
    }
    public void getPay(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Card card = cardRepository.findByUser(user).orElseThrow();
        List<CardItem> cardItems = cardItemRepository.findByCard(card);


        cardItems.forEach(cardItem -> {
            Payment payment = new Payment();
            Product product = productRepository.findById(cardItem.getProduct().getId()).orElseThrow();
            payment.setProduct(cardItem.getProduct());
            payment.setQuantity(cardItem.getQuantity());
            payment.setCard(cardItem.getCard());
            payment.setSuccess(true);
            product.setStock(product.getStock() - cardItem.getQuantity());
            paymentRepository.save(payment);
        });
        card.getCardItems().removeAll(cardItemRepository.findByCard(card));
        cardItemRepository.deleteAll(cardItems);
    }
}


