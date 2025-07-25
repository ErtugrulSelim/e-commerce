package com.example.eticaret.service;

import com.example.eticaret.dto.CardItemDto;
import com.example.eticaret.model.Card;
import com.example.eticaret.model.CardItem;
import com.example.eticaret.model.Product;
import com.example.eticaret.model.User;
import com.example.eticaret.repository.CardItemRepository;
import com.example.eticaret.repository.CardRepository;
import com.example.eticaret.repository.UserRepository;
import com.example.eticaret.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class CardService {

    private CardRepository cardRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CardItemRepository cardItemRepository;

    public void GetSets() {

    }
    public List<CardItemDto> getMappingCardItemDto() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Card card = cardRepository.findByUser(user).orElseThrow();
        List<CardItem> cardItems = cardItemRepository.findByCard(card);
        return cardItems.stream().map(cardItem ->  {
            CardItemDto dto = new CardItemDto();
            dto.setCard(cardItem.getCard());
            dto.setQuantity(cardItem.getQuantity());
            dto.setProduct(cardItem.getProduct());
            return dto;
        }).collect(Collectors.toList());
    }
    public List<CardItemDto> getCardItems() {
        return getMappingCardItemDto();
    }

    public void addToCard(Long productId, int quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow();

        Card card = cardRepository.findByUser(user).orElseGet(()->{
            Card newCard = new Card();
            newCard.setUser(user);
            newCard.setCardItems(new ArrayList<>());
            return cardRepository.save(newCard);
        });

        List<CardItem> cardItem = cardItemRepository.findByCard(card);
        Product product = productRepository.findById(productId).orElseThrow();

            Optional<CardItem> sameProductId = cardItem.stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();
            if(sameProductId.isPresent()){
                CardItem exactCardItem = sameProductId.get();
                exactCardItem.setCard(card);
                if(product.getStock() > quantity && exactCardItem.getQuantity() < product.getStock()){
                    exactCardItem.setQuantity(exactCardItem.getQuantity() + quantity);
                }
                else {
                    throw new RuntimeException("There is no product stock with that quantity");
                }
                cardItemRepository.save(exactCardItem);
            }
            else {
                CardItem newItem = new CardItem();
                newItem.setProduct(product);
                if(product.getStock() > quantity &&  newItem.getQuantity() < product.getStock()) {
                    newItem.setQuantity(quantity);
                }
                else {
                    throw new RuntimeException("There is no product stock with that quantity");
                }
                newItem.setCard(card);
                cardItemRepository.save(newItem);

                card.getCardItems().add(newItem);
                cardRepository.save(card);
            }

        }
        public void deleteProductfromCard(Long productId, int quantity) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            User user = userRepository.findByUsername(username).orElseThrow();
            Card card = cardRepository.findByUser(user).orElseThrow();
            List<CardItem> cardItem = cardItemRepository.findByCard(card);

            Optional<CardItem> sameProductId = cardItem.stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();
            if(sameProductId.isPresent()
                    && sameProductId.get().getQuantity() <= quantity) {
                CardItem exactCardItem = sameProductId.get();
                card.getCardItems().remove(exactCardItem);
                cardItemRepository.delete(exactCardItem);
            }
            else {
                CardItem exactCardItem = sameProductId.get();
                exactCardItem.setQuantity(exactCardItem.getQuantity()-quantity);
                cardItemRepository.save(exactCardItem);
            }

        }
        public void cleanCard () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow();
        Card card = cardRepository.findByUser(user).orElseThrow();
        List<CardItem> cardItem = cardItemRepository.findByCard(card);
        card.getCardItems().removeAll(cardItemRepository.findByCard(card));
        cardItemRepository.deleteAll(cardItem);
        }

}

