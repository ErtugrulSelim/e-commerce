package com.example.eticaret.repository;

import com.example.eticaret.model.Card;
import com.example.eticaret.model.Product;
import com.example.eticaret.model.User;
import com.example.eticaret.model.CardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository

public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByUser(User user);
}
