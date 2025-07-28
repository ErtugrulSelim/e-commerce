package com.example.eticaret.controller;

import com.example.eticaret.dto.PaymentDto;
import com.example.eticaret.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payment")

public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @GetMapping
    public List<PaymentDto> getPayments() {
        return paymentService.getPastPayments();
    }
    @PostMapping("get-pay-product")
    public void getPayProduct(@RequestParam Long productId, @RequestParam int quantity) {
        paymentService.getPayProduct(productId,quantity);
    }
    @PostMapping
    public void getPay() {
        paymentService.getPay();
    }

}
