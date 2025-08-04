package com.example.eticaret.repository;

import com.example.eticaret.model.Cart;
import com.example.eticaret.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(User user);
}
