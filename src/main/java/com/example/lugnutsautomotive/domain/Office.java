package com.example.lugnutsautomotive.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Office {
    @Id
    @Column(length = 10)
    private String officeCode;

    @Column(length = 50, nullable = false)
    private String city;

    @Column(length = 50, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String addressLine1;

    @Column(length = 50)
    private String addressLine2;

    @Column(length = 50)
    private String state;

    @Column(length = 50, nullable = false)
    private String country;

    @Column(length = 15, nullable = false)
    private String postalCode;

    @Column(length = 10, nullable = false)
    private String territory;

    public String getOfficeCode() { return officeCode; }
    public void setOfficeCode(String officeCode) { this.officeCode = officeCode; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getTerritory() { return territory; }
    public void setTerritory(String territory) { this.territory = territory; }
}