package com.example.eticaret.repository;

import com.example.eticaret.dto.PaymentDto;
import com.example.eticaret.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.eticaret.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCart(Cart cart);

    List<Payment> getByIsSuccess(boolean isSuccess);
}
