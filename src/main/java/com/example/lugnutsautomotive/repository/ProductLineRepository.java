package com.example.lugnutsautomotive.repository;

import com.example.lugnutsautomotive.domain.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLineRepository extends JpaRepository<ProductLine, String> {
}