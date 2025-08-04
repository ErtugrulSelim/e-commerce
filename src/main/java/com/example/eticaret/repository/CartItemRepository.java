package com.example.eticaret.repository;

import com.example.eticaret.model.Cart;
import com.example.eticaret.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart card);
}
