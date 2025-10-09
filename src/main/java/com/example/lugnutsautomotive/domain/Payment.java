package com.example.lugnutsautomotive.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Payment {
    @EmbeddedId
    private PaymentId id;

    @ManyToOne(optional = false)
    @MapsId("customerNumber")
    @JoinColumn(name = "customerNumber")
    private Customer customer;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    public PaymentId getId() { return id; }
    public void setId(PaymentId id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}