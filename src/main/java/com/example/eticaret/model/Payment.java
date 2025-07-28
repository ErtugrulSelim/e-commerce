package com.example.eticaret.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="payments")
@NoArgsConstructor
@AllArgsConstructor

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isSuccess;
    @ManyToOne
    @JsonBackReference
    private Product product;
    private int quantity;
    @ManyToOne
    @JsonBackReference
    private Card card;
}
