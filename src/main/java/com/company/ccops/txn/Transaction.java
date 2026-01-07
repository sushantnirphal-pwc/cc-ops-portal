package com.company.ccops.txn;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "txn")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String cardId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private String mcc;

    @Column(nullable = false)
    private String merchantName;

    @Column(nullable = false)
    private Instant txnTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TxnStatus status;

    @Column(nullable = false)
    private String authCode;

    protected Transaction() {}

    public Transaction(String accountId, String cardId, BigDecimal amount, String currency, String mcc,
                       String merchantName, Instant txnTime, TxnStatus status, String authCode) {
        this.accountId = accountId;
        this.cardId = cardId;
        this.amount = amount;
        this.currency = currency;
        this.mcc = mcc;
        this.merchantName = merchantName;
        this.txnTime = txnTime;
        this.status = status;
        this.authCode = authCode;
    }

    public String getId() { return id; }
    public String getAccountId() { return accountId; }
    public String getCardId() { return cardId; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getMcc() { return mcc; }
    public String getMerchantName() { return merchantName; }
    public Instant getTxnTime() { return txnTime; }
    public TxnStatus getStatus() { return status; }
    public String getAuthCode() { return authCode; }
}
