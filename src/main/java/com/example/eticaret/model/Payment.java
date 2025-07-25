package com.example.eticaret.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Payment {
    @Id
    private Long id;
    private Date date;
    private boolean isSuccess;
    private boolean isRequest;
    @OneToOne
    private Card card;
}
