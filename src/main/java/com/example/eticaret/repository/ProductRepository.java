package com.example.eticaret.repository;

import com.example.eticaret.model.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(@NotNull(message = "Please provide a name") String name);
}