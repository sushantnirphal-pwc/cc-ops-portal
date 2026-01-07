package com.company.ccops.audit;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "audit_event")
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private String actor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditAction action;

    @Column(nullable = false)
    private String entityType;

    @Column(nullable = false)
    private String entityId;

    @Column(nullable = false)
    private String correlationId;

    @Column(nullable = false)
    private String outcome;

    @Column(length = 2000)
    private String detailsJson;

    protected AuditEvent() {}

    public AuditEvent(Instant timestamp, String actor, AuditAction action, String entityType, String entityId,
                      String correlationId, String outcome, String detailsJson) {
        this.timestamp = timestamp;
        this.actor = actor;
        this.action = action;
        this.entityType = entityType;
        this.entityId = entityId;
        this.correlationId = correlationId;
        this.outcome = outcome;
        this.detailsJson = detailsJson;
    }

    public String getId() { return id; }
    public Instant getTimestamp() { return timestamp; }
    public String getActor() { return actor; }
    public AuditAction getAction() { return action; }
    public String getEntityType() { return entityType; }
    public String getEntityId() { return entityId; }
    public String getCorrelationId() { return correlationId; }
    public String getOutcome() { return outcome; }
    public String getDetailsJson() { return detailsJson; }
}
