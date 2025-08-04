package com.example.eticaret.model;

import com.example.eticaret.Enum.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Please provide a category")
    private Category category;
    @NotNull(message = "Please provide a name")
    private String name;
    @NotNull(message = "Please provide a price")
    @Column(name = "price")
    private Integer price;
    @Column(name = "stock")
    @Max(value = 10000,message = "The stock have to less 10000")
    private long stock;
}
