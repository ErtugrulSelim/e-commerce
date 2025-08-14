package com.example.eticaret.repository;

import com.example.eticaret.model.Feedback;
import com.example.eticaret.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query(value = "SELECT SUM(rating) FROM feedback WHERE feedback.product_id = ?1", nativeQuery = true)
    int getTotalScore(long product_id);

    @Query(value = "SELECT COUNT(product_id) FROM feedback WHERE feedback.product_id = ?1", nativeQuery = true)
    int getTotalCount(long product_id);

    Feedback findByPaymentId(long paymentId);

    List<Feedback> findByProduct_Id(long productId);

    Feedback findByPaymentIdAndUser(long paymentId, User user);

}
