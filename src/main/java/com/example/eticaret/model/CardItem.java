package com.example.eticaret.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CardItem {
    private Product product;
    private int quantity;
}
    