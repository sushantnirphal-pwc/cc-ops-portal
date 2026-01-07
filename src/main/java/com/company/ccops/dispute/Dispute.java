package com.company.ccops.dispute;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "dispute")
public class Dispute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String txnId;

    @Column(nullable = false)
    private String reasonCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DisputeStatus status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private Instant createdAt;

    protected Dispute() {}

    public Dispute(String customerId, String accountId, String txnId, String reasonCode, DisputeStatus status, BigDecimal amount, Instant createdAt) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.txnId = txnId;
        this.reasonCode = reasonCode;
        this.status = status;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public String getAccountId() { return accountId; }
    public String getTxnId() { return txnId; }
    public String getReasonCode() { return reasonCode; }
    public DisputeStatus getStatus() { return status; }
    public BigDecimal getAmount() { return amount; }
    public Instant getCreatedAt() { return createdAt; }
}
