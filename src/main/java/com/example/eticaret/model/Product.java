package com.example.eticaret.model;

import com.example.eticaret.Enum.Category;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name="products")
@NoArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Category category;
    private String name;
    @Column(name = "price")
    private Integer price;
    @Column(name = "stock")
    private Integer stock;
}
