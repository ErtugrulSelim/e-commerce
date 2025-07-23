package com.example.eticaret.controller;

import com.example.eticaret.model.Card;
import com.example.eticaret.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/card")
public class CardController {
    private final CardService cardService;
    public CardController(CardService cardService) {

        this.cardService = cardService;
    }
    @GetMapping
    public List<Card> getAllProductsfromCards() {
        return cardService.getAllProductsfromCards();
    }
    @PostMapping("/{productId}")
        public ResponseEntity<String> placeCard(@PathVariable long productId) {
        String saved = cardService.createCard(productId);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteCard(@PathVariable long productId) {
        String saved = cardService.deleteCard(productId);
        return ResponseEntity.ok("Card deleted");
    }
//        public ResponseEntity<String> deleteOrder(@PathVariable long productId) {
//        return ResponseEntity.ok("Card deleted successfully");
//    }

}

