package com.example.eticaret.repository;

import com.example.eticaret.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.eticaret.model.Payment;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByCard(Card card);
}
