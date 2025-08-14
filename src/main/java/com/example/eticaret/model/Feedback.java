package com.example.eticaret.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("product-feedback")
    private Product product;

    @ManyToOne
    @JsonBackReference("user-feedback")
    private User user;

    @OneToOne
    @JsonBackReference("payment-feedback")
    private Payment payment;

    private String comment;

    @Min(value = 1, message = "Can't sign lower than 1")
    @Max(value = 5, message = "Can't sign bigger than 5 ")
    private int rating;
}
