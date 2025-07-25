package com.example.eticaret.dto;

import com.example.eticaret.Enum.Category;
import lombok.*;

@Data

public class ProductDto {
    private String name;
    private Category category;
    private Integer price;
    private Integer stock;
}
