package com.example.lugnutsautomotive.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Customer {
    @Id
    private Integer customerNumber;

    @Column(length = 50, nullable = false)
    private String customerName;

    @Column(length = 50, nullable = false)
    private String contactLastName;

    @Column(length = 50, nullable = false)
    private String contactFirstName;

    @Column(length = 50, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String addressLine1;

    @Column(length = 50)
    private String addressLine2;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 50)
    private String state;

    @Column(length = 15)
    private String postalCode;

    @Column(length = 50, nullable = false)
    private String country;

    @ManyToOne
    @JoinColumn(name = "salesRepEmployeeNumber")
    private Employee salesRep;

    @Column(precision = 10, scale = 2)
    private BigDecimal creditLimit;

    public Integer getCustomerNumber() { return customerNumber; }
    public void setCustomerNumber(Integer customerNumber) { this.customerNumber = customerNumber; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getContactLastName() { return contactLastName; }
    public void setContactLastName(String contactLastName) { this.contactLastName = contactLastName; }
    public String getContactFirstName() { return contactFirstName; }
    public void setContactFirstName(String contactFirstName) { this.contactFirstName = contactFirstName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Employee getSalesRep() { return salesRep; }
    public void setSalesRep(Employee salesRep) { this.salesRep = salesRep; }
    public BigDecimal getCreditLimit() { return creditLimit; }
    public void setCreditLimit(BigDecimal creditLimit) { this.creditLimit = creditLimit; }
}