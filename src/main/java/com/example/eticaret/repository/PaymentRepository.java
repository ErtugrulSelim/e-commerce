package com.example.eticaret.repository;

import com.example.eticaret.model.Cart;
import com.example.eticaret.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.eticaret.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCart(Cart cart);

    List<Payment> getByIsSuccess(boolean isSuccess);

    Payment findByUser(User user);

    Payment findById(long paymentId);
}