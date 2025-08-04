package com.example.eticaret.dto;

import com.example.eticaret.model.Cart;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class PaymentDto {
    public long id;
    public ProductDto product;
    public long quantity;
    public boolean isSuccess;
    @JsonBackReference
    public Cart cart;
}
