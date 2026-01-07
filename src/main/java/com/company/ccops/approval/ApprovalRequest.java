package com.company.ccops.approval;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "approval_request")
public class ApprovalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String policyKey;

    @Column(nullable = false)
    private String payloadRef;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status;

    @Column(nullable = false)
    private String requestedBy;

    private String decidedBy;
    private String decisionNote;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant decidedAt;

    protected ApprovalRequest() {}

    public ApprovalRequest(String policyKey, String payloadRef, ApprovalStatus status, String requestedBy, Instant createdAt) {
        this.policyKey = policyKey;
        this.payloadRef = payloadRef;
        this.status = status;
        this.requestedBy = requestedBy;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public String getPolicyKey() { return policyKey; }
    public String getPayloadRef() { return payloadRef; }
    public ApprovalStatus getStatus() { return status; }
    public String getRequestedBy() { return requestedBy; }
    public String getDecidedBy() { return decidedBy; }
    public String getDecisionNote() { return decisionNote; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getDecidedAt() { return decidedAt; }

    public void approve(String actor, String note) {
        this.status = ApprovalStatus.APPROVED;
        this.decidedBy = actor;
        this.decisionNote = note;
        this.decidedAt = Instant.now();
    }

    public void reject(String actor, String note) {
        this.status = ApprovalStatus.REJECTED;
        this.decidedBy = actor;
        this.decisionNote = note;
        this.decidedAt = Instant.now();
    }
}
