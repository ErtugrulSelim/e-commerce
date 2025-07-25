package com.example.eticaret.controller;

import com.example.eticaret.dto.CardItemDto;
import com.example.eticaret.model.Card;
import com.example.eticaret.model.CardItem;
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
    public List<CardItemDto> getCardItems() {
        return cardService.getCardItems();
    }
    @PostMapping("/add")
        public void addToCard(@RequestParam long productId, @RequestParam int quantity) {
        cardService.addToCard(productId, quantity);
    }
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String> deleteProductfromCard(@RequestParam long productId, @RequestParam int quantity) {
        cardService.deleteProductfromCard(productId, quantity);
        return ResponseEntity.ok("Card deleted");
    }
    @DeleteMapping("/cleanCard")
    public ResponseEntity<String> cleanCard() {
        cardService.cleanCard();
        return ResponseEntity.ok("Card cleaned");
    }

}

