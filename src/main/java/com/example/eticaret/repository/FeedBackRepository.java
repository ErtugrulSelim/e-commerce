package com.example.eticaret.repository;

import com.example.eticaret.model.FeedBack;
import com.example.eticaret.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {

    @Query(value = "SELECT SUM(rating) FROM feed_back WHERE feed_back.product_id = ?1", nativeQuery = true)
    int getTotalScore(long product_id);

    @Query(value = "SELECT COUNT(product_id) FROM feed_back WHERE feed_back.product_id = ?1", nativeQuery = true)
    int getTotalCount(long product_id);

    FeedBack findByPaymentId(long paymentId);

    List<FeedBack> findByProduct_Id(long productId);

    FeedBack findByPaymentIdAndUser(long paymentId, User user);
}
