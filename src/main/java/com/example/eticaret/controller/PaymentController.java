package com.example.eticaret.controller;

import com.example.eticaret.dto.PaymentDto;
import com.example.eticaret.model.User;
import com.example.eticaret.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payment")

public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {

        this.paymentService = paymentService;
    }

    @GetMapping("/get-past-pays")
    public List<PaymentDto> getPastPays(@AuthenticationPrincipal User user) {
        return paymentService.getPastPays(user);
    }

    @GetMapping("/get-pending-pays")
    public List<PaymentDto> getPendingPays(@AuthenticationPrincipal User user) {
        return paymentService.getPendingPayments(user);
    }

    @PostMapping("/get-pay-product")
    public ResponseEntity<String> getPayProductRequest(@Valid @AuthenticationPrincipal User user,
                                                       @RequestParam Long productId, @RequestParam int quantity) {
        paymentService.getPayProductRequest(user, productId, quantity);
        return ResponseEntity.ok("Product requested by id" + productId);
    }

    @PostMapping
    public ResponseEntity<String> getPayRequestAll(@Valid @AuthenticationPrincipal User user) {
        paymentService.getPayRequestAll(user);
        return ResponseEntity.ok("All product requested.");
    }

    @PutMapping
    public ResponseEntity<String> getPayComplete(@Valid @AuthenticationPrincipal User user) {
        paymentService.getPayComplete(user);
        return ResponseEntity.ok("Payments complete.");
    }

    @DeleteMapping("/delete-pending-pay-byId/{productId}")
    public ResponseEntity<String> deletePendingPayById(@Valid @AuthenticationPrincipal User user,
                                                       @PathVariable Long productId) {
        paymentService.deletePendingPayById(user, productId);
        return ResponseEntity.ok("Deleted pending payment by id " + productId);
    }

    @DeleteMapping("/delete-all-pending-pays")
    public ResponseEntity<String> deleteAllPendingPays(@Valid @AuthenticationPrincipal User user) {
        paymentService.deleteAllPendingPays(user);
        return ResponseEntity.ok("Deleted all pending pays.");
    }
}
