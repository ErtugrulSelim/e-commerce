package com.example.eticaret.dto;

import com.example.eticaret.Enum.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDto {
    private String id;
    private String name;
    private Category category;
    private Integer price;
}
