package com.example.eticaret.dto;

import com.example.eticaret.model.Card;
import com.example.eticaret.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data

public class CardItemDto {
    private Product product;
    private int quantity;
    @JsonBackReference
    private Card card;
}
