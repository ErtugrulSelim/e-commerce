package com.example.eticaret.dto;

import lombok.Data;

@Data
public class MailDto {
    private String toEmail;
    private String subject;
    private String text;
}
