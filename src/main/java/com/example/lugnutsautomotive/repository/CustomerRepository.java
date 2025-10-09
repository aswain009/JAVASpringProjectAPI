package com.example.lugnutsautomotive.repository;

import com.example.lugnutsautomotive.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByCustomerNameContainingIgnoreCase(String name);
}