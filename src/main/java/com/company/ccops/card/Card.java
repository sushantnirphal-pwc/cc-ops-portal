package com.company.ccops.card;

import jakarta.persistence.*;

@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false, unique = true)
    private String cardToken;

    @Column(nullable = false)
    private String last4;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardStatus status;

    @Column(nullable = false)
    private String embossName;

    @Column(nullable = false)
    private int expiryMonth;

    @Column(nullable = false)
    private int expiryYear;

    protected Card() {}

    public Card(String accountId, String cardToken, String last4, CardStatus status,
                String embossName, int expiryMonth, int expiryYear) {
        this.accountId = accountId;
        this.cardToken = cardToken;
        this.last4 = last4;
        this.status = status;
        this.embossName = embossName;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
    }

    public String getId() { return id; }
    public String getAccountId() { return accountId; }
    public String getCardToken() { return cardToken; }
    public String getLast4() { return last4; }
    public CardStatus getStatus() { return status; }
    public String getEmbossName() { return embossName; }
    public int getExpiryMonth() { return expiryMonth; }
    public int getExpiryYear() { return expiryYear; }

    public void setStatus(CardStatus status) { this.status = status; }
}
