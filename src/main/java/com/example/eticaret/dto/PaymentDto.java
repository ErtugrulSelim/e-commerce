package com.example.eticaret.dto;

import com.example.eticaret.model.Cart;
import com.example.eticaret.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class PaymentDto {
    public ProductDto product;
    public int quantity;
    public boolean isSuccess;
    @JsonBackReference
    public Cart cart;
}
