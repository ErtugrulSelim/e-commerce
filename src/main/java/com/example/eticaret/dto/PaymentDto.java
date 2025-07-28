package com.example.eticaret.dto;

import com.example.eticaret.model.Card;
import com.example.eticaret.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class PaymentDto {
    public Product product;
    public int quantity;
    public boolean isSuccess;
    @JsonBackReference
    public Card card;
}
