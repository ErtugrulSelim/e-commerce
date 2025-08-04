package com.example.eticaret.dto;

import com.example.eticaret.model.Cart;
import com.example.eticaret.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data

public class CartItemDto {
    private ProductDto product;
    private long quantity;
    @JsonBackReference
    private Cart cart;
}
