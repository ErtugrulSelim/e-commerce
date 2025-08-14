package com.example.eticaret.dto;

import lombok.Data;

@Data
public class FeedbackDto {
    private Long id;
    private String comment;
    private int rating;
    private Long productId;
    private Long userId;
    private Long paymentId;
}
