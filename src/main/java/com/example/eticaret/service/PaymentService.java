package com.example.eticaret.service;

import com.example.eticaret.dto.PaymentDto;
import com.example.eticaret.dto.ProductDto;
import com.example.eticaret.exceptions.MoneyException;
import com.example.eticaret.exceptions.NotFoundException;
import com.example.eticaret.exceptions.PendingException;
import com.example.eticaret.exceptions.StockException;
import com.example.eticaret.model.*;
import com.example.eticaret.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class PaymentService {
    private PaymentRepository paymentRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;
    private ProductService productService;
    private UserRepository userRepository;

    private Cart getCurrentUserCart(User user) {
        return cartRepository.findByUser(user);
    }

    private List<PaymentDto> getPayments(User user) {
        Cart cart = getCurrentUserCart(user);
        List<Payment> payments = paymentRepository.findByCart(cart);

        return payments.stream().map(payment -> {
            PaymentDto dto = new PaymentDto();
            dto.setId(payment.getId());
            dto.setSuccess(payment.isSuccess());
            dto.setQuantity(payment.getQuantity());
            dto.setCart(payment.getCart());
            ProductDto productDto = productService.convertToDto(payment.getProduct());
            dto.setProduct(productDto);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<PaymentDto> getPastPays(User user) {
        List<PaymentDto> paymentDto = getPayments(user);
        return paymentDto.stream()
                .filter(PaymentDto::isSuccess)
                .collect(Collectors.toList());
    }

    public List<PaymentDto> getPendingPayments(User user) {
        List<PaymentDto> paymentDto = getPayments(user);
        return paymentDto.stream()
                .filter(payment -> !payment.isSuccess())
                .collect(Collectors.toList());
    }

    private Boolean pendingStateOfPays(long productId, Cart cart) {
        boolean alreadyPending = paymentRepository
                .findByCart(cart)
                .stream()
                .anyMatch(p -> p.getProduct().getId() == productId && !p.isSuccess());
        return alreadyPending;
    }
    private void createPayment(long quantity, Cart cart, Product product) {
        Payment payment = new Payment();
        payment.setProduct(product);
        payment.setQuantity(quantity);
        payment.setCart(cart);
        payment.setSuccess(false);
        paymentRepository.save(payment);
    }


    @Transactional
    public void getPayProductRequest(User user, long productId, int quantity) {
        Cart cart = getCurrentUserCart(user);
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        if (cartItems.isEmpty()) {
            throw new NotFoundException("There is no product at the cart.");
        }
        boolean pendingState = pendingStateOfPays(productId, cart);
        if (pendingState) {
            throw new PendingException
                    ("This product already has a pending payment." + getPendingPayments(user));
        }
        Product product = productRepository.findById(productId).orElseThrow();
        createPayment(quantity, cart, product);
    }

    @Transactional
    public void getPayRequestAll(User user) {
        Cart cart = getCurrentUserCart(user);
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        List<Payment> payments = paymentRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new NotFoundException("There is no product at the cart.");
        }
        cartItems.forEach(cartItem -> {
            boolean hasPending = payments.stream()
                    .anyMatch(p -> p.getProduct().getId() == cartItem.getProduct().getId() && !p.isSuccess());

            if (hasPending) {
                throw new PendingException("These products already have a pending payment." + getPendingPayments(user));
            }
            createPayment(cartItem.getQuantity(), cartItem.getCart(), cartItem.getProduct());
        });
    }

    @Transactional
    public void getPayComplete(User user) {
        Cart cart = getCurrentUserCart(user);
        List<Payment> pendingPayments = paymentRepository.getByIsSuccess(false);

        if (pendingPayments.isEmpty()) {
            throw new NotFoundException("There is no pending payment.");
        }
        for (Payment payment : pendingPayments) {
            Product product = payment.getProduct();
            long quantity = payment.getQuantity();

            if (product.getStock() < quantity) {
                throw new StockException("Insufficient stock for product: " + product.getName());
            } else if (user.getMoney() < product.getPrice() * quantity) {
                throw new MoneyException("Insufficient money for product.");
            }
            user.setMoney(user.getMoney() - product.getPrice() * quantity);
            userRepository.save(user);

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            payment.setSuccess(true);
            paymentRepository.save(payment);

            List<CartItem> cartItems = cartItemRepository.findByCart(cart);
            cartItems.forEach(cartItem -> {
                long remaining = cartItem.getQuantity() - quantity;
                if (remaining <= 0) {
                    cart.getCartItems().remove(cartItem);
                    cartItemRepository.delete(cartItem);
                } else {
                    cartItem.setQuantity(remaining);
                    cartItemRepository.save(cartItem);
                }
            });
        }
    }

    @Transactional
    public void deletePendingPayById(User user, long paymentId) {
        Cart cart = getCurrentUserCart(user);
        boolean pendingState = pendingStateOfPays(paymentId, cart);
        if (pendingState) {
            paymentRepository.deleteById(paymentId);
        } else {
            throw new NotFoundException("There is no pending payment at the cart.");
        }
    }

    @Transactional
    public void deleteAllPendingPays(User user) {
        Cart cart = getCurrentUserCart(user);
        List<Payment> payments = paymentRepository.findByCart(cart);

        List<Payment> pendingPayments = payments.stream()
                .filter(payment -> !payment.isSuccess())
                .collect(Collectors.toList());
        if (pendingPayments.isEmpty()) {
            throw new NotFoundException("There are no pending payments in the cart.");
        }
        paymentRepository.deleteAll(pendingPayments);
    }
}


