package com.company.ccops.customer;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String kycStatus;

    @Column(nullable = false)
    private String riskTier;

    @Column(nullable = false)
    private Instant createdAt;

    protected Customer() {}

    public Customer(String fullName, String email, String phone, String kycStatus, String riskTier, Instant createdAt) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.kycStatus = kycStatus;
        this.riskTier = riskTier;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getKycStatus() { return kycStatus; }
    public String getRiskTier() { return riskTier; }
    public Instant getCreatedAt() { return createdAt; }
}
