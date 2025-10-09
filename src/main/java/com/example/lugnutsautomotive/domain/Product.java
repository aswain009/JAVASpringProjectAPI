package com.example.lugnutsautomotive.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @Column(length = 15)
    private String productCode;

    @Column(length = 70, nullable = false)
    private String productName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "productLine", nullable = false)
    private ProductLine productLine;

    @Column(length = 50, nullable = false)
    private String productVendor;

    @Lob
    @Column(nullable = false)
    private String productDescription;

    private int quantityInStock;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal buyPrice;

    @Column(name = "MSRP", precision = 10, scale = 2, nullable = false)
    private BigDecimal msrp;

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public ProductLine getProductLine() { return productLine; }
    public void setProductLine(ProductLine productLine) { this.productLine = productLine; }
    public String getProductVendor() { return productVendor; }
    public void setProductVendor(String productVendor) { this.productVendor = productVendor; }
    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
    public int getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(int quantityInStock) { this.quantityInStock = quantityInStock; }
    public BigDecimal getBuyPrice() { return buyPrice; }
    public void setBuyPrice(BigDecimal buyPrice) { this.buyPrice = buyPrice; }
    public BigDecimal getMsrp() { return msrp; }
    public void setMsrp(BigDecimal msrp) { this.msrp = msrp; }
}