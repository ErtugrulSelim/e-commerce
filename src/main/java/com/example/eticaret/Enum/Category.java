package com.example.eticaret.Enum;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)

public enum Category {
    Electronic,
    Furniture,
    Spor,
    Games
}