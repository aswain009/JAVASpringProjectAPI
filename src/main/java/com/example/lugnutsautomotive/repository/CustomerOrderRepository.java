package com.example.lugnutsautomotive.repository;

import com.example.lugnutsautomotive.domain.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer> {
    List<CustomerOrder> findByCustomer_CustomerNumber(Integer customerNumber);
}