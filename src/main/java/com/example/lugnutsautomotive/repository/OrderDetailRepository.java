package com.example.lugnutsautomotive.repository;

import com.example.lugnutsautomotive.domain.OrderDetail;
import com.example.lugnutsautomotive.domain.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrder_OrderNumber(Integer orderNumber);
}