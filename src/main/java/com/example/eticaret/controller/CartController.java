package com.example.eticaret.controller;

import com.example.eticaret.dto.CartItemDto;
import com.example.eticaret.model.User;
import com.example.eticaret.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {

        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItemDto> getCartItems(@AuthenticationPrincipal User user) {
        return cartService.getMappingCartItemDto(user);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@Valid @AuthenticationPrincipal User user,
                                            @RequestParam long productId, @RequestParam int quantity) {
        cartService.addToCart(user, productId, quantity);
        return ResponseEntity.ok("Successfully added to cart.");
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<String> deleteProductFromCart(@Valid @AuthenticationPrincipal User user,
                                                        @RequestParam long productId, @RequestParam int quantity) {
        cartService.deleteProductFromCart(user, productId, quantity);
        return ResponseEntity.ok("Cart deleted");
    }

    @DeleteMapping("/clean-cart")
    public ResponseEntity<String> cleanCart(@Valid @AuthenticationPrincipal User user) {
        cartService.cleanCart(user);
        return ResponseEntity.ok("Cart cleaned.");
    }

}

