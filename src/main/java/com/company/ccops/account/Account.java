package com.company.ccops.account;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String productType;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal creditLimit;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal availableLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Column(nullable = false)
    private int billingDay;

    @Column(nullable = false)
    private Instant createdAt;

    protected Account() {}

    public Account(String customerId, String productType, BigDecimal creditLimit, BigDecimal availableLimit,
                   AccountStatus status, int billingDay, Instant createdAt) {
        this.customerId = customerId;
        this.productType = productType;
        this.creditLimit = creditLimit;
        this.availableLimit = availableLimit;
        this.status = status;
        this.billingDay = billingDay;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getProductType() { return productType; }
    public java.math.BigDecimal getCreditLimit() { return creditLimit; }
    public java.math.BigDecimal getAvailableLimit() { return availableLimit; }
    public AccountStatus getStatus() { return status; }
    public int getBillingDay() { return billingDay; }
    public Instant getCreatedAt() { return createdAt; }
}
