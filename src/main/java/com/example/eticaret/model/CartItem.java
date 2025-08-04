package com.example.eticaret.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    @Max(value = 200,message = "The quantity have to less than 200")
    private long quantity;
    @ManyToOne
    @JsonBackReference
    private Cart cart;
}
