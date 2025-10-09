package com.example.lugnutsautomotive.repository;

import com.example.lugnutsautomotive.domain.Payment;
import com.example.lugnutsautomotive.domain.PaymentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, PaymentId> {
    List<Payment> findByCustomer_CustomerNumber(Integer customerNumber);
}