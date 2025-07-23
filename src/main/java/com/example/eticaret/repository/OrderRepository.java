package com.example.eticaret.repository;

import com.example.eticaret.model.Order;
import com.example.eticaret.model.Product;
import com.example.eticaret.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUserAndProductsContaining(User user, Product product);

}
