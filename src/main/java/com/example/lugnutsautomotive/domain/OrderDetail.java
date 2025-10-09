package com.example.lugnutsautomotive.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orderdetails")
public class OrderDetail {
    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne(optional = false)
    @MapsId("orderNumber")
    @JoinColumn(name = "orderNumber")
    private CustomerOrder order;

    @ManyToOne(optional = false)
    @MapsId("productCode")
    @JoinColumn(name = "productCode")
    private Product product;

    @Column(nullable = false)
    private Integer quantityOrdered;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal priceEach;

    @Column(nullable = false)
    private Short orderLineNumber;

    public OrderDetailId getId() { return id; }
    public void setId(OrderDetailId id) { this.id = id; }
    public CustomerOrder getOrder() { return order; }
    public void setOrder(CustomerOrder order) { this.order = order; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public Integer getQuantityOrdered() { return quantityOrdered; }
    public void setQuantityOrdered(Integer quantityOrdered) { this.quantityOrdered = quantityOrdered; }
    public BigDecimal getPriceEach() { return priceEach; }
    public void setPriceEach(BigDecimal priceEach) { this.priceEach = priceEach; }
    public Short getOrderLineNumber() { return orderLineNumber; }
    public void setOrderLineNumber(Short orderLineNumber) { this.orderLineNumber = orderLineNumber; }
}