package com.example.eticaret.repository;

import com.example.eticaret.model.Card;
import com.example.eticaret.model.Product;
import com.example.eticaret.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByProducts(Product product);

    List<Card> findByUserUsername(String username);

    Optional<Card> findByUser(User user);
}
