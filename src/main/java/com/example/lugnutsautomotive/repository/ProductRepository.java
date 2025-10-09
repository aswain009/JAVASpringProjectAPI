package com.example.lugnutsautomotive.repository;

import com.example.lugnutsautomotive.domain.Product;
import com.example.lugnutsautomotive.domain.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByProductLine(ProductLine productLine);
    List<Product> findByProductNameContainingIgnoreCase(String name);
}