package com.example.eticaret.repository;

import com.example.eticaret.model.Card;
import com.example.eticaret.model.CardItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardItemRepository extends JpaRepository<CardItem ,Long> {

    List<CardItem> findByCard(Card card);
}
