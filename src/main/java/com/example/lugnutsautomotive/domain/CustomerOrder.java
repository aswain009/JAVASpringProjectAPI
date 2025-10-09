package com.example.lugnutsautomotive.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class CustomerOrder {
    @Id
    private Integer orderNumber;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false)
    private LocalDate requiredDate;

    private LocalDate shippedDate;

    @Column(length = 15, nullable = false)
    private String status;

    @Lob
    private String comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerNumber", nullable = false)
    private Customer customer;

    public Integer getOrderNumber() { return orderNumber; }
    public void setOrderNumber(Integer orderNumber) { this.orderNumber = orderNumber; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public LocalDate getRequiredDate() { return requiredDate; }
    public void setRequiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; }
    public LocalDate getShippedDate() { return shippedDate; }
    public void setShippedDate(LocalDate shippedDate) { this.shippedDate = shippedDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}