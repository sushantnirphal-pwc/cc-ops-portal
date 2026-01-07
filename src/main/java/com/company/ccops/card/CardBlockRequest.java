package com.company.ccops.card;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "card_block")
public class CardBlockRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String cardId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BlockReason reason;

    @Column(nullable = false)
    private String note;

    @Column(nullable = false)
    private String requestedBy;

    private String decidedBy;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant decidedAt;

    protected CardBlockRequest() {}

    public CardBlockRequest(String cardId, BlockReason reason, String note, String requestedBy, String status, Instant createdAt) {
        this.cardId = cardId;
        this.reason = reason;
        this.note = note;
        this.requestedBy = requestedBy;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getCardId() { return cardId; }
    public BlockReason getReason() { return reason; }
    public String getNote() { return note; }
    public String getRequestedBy() { return requestedBy; }
    public String getDecidedBy() { return decidedBy; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getDecidedAt() { return decidedAt; }

    public void markApproved(String actor) {
        this.status = "APPROVED";
        this.decidedBy = actor;
        this.decidedAt = Instant.now();
    }
    public void markRejected(String actor) {
        this.status = "REJECTED";
        this.decidedBy = actor;
        this.decidedAt = Instant.now();
    }
    public void markExecuted() {
        this.status = "EXECUTED";
    }
}
