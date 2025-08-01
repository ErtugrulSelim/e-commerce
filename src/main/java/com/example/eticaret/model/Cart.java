package com.example.eticaret.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "cart")
@NoArgsConstructor

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @OneToMany
    @JsonManagedReference
    @Size(max = 15,message = "The cart items have to less than 15")
    private List<CartItem> cartItems;
}
