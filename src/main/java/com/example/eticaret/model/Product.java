package com.example.eticaret.model;

import com.example.eticaret.Enum.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private Category category;
    private String name;
    @NotNull
    @Column(name = "price")
    private Integer price;
    @Column(name = "stock")
    @Max(value = 10000)
    private int stock;
}
