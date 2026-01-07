package com.company.ccops.approval;

import com.company.ccops.audit.AuditAction;
import com.company.ccops.audit.AuditService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ApprovalService {

    private final ApprovalRepository repo;
    private final AuditService audit;

    public ApprovalService(ApprovalRepository repo, AuditService audit) {
        this.repo = repo;
        this.audit = audit;
    }

    @Transactional
    public ApprovalRequest create(String policyKey, String payloadRef) {
        String actor = SecurityContextHolder.getContext().getAuthentication().getName();
        ApprovalRequest req = repo.save(new ApprovalRequest(policyKey, payloadRef, ApprovalStatus.PENDING, actor, Instant.now()));
        audit.record(AuditAction.APPROVAL_REQUESTED, "ApprovalRequest", req.getId(), "OK",
                "{\"policyKey\":\"" + policyKey + "\",\"payloadRef\":\"" + payloadRef + "\"}");
        return req;
    }

    @PreAuthorize("hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public List<ApprovalRequest> listPending() {
        return repo.findByStatusOrderByCreatedAtDesc(ApprovalStatus.PENDING);
    }

    @PreAuthorize("hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    @Transactional
    public ApprovalRequest approve(String id, String note) {
        ApprovalRequest req = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Approval not found: " + id));
        if (req.getStatus() != ApprovalStatus.PENDING) throw new IllegalArgumentException("Approval not pending");
        String actor = SecurityContextHolder.getContext().getAuthentication().getName();
        req.approve(actor, note);
        audit.record(AuditAction.APPROVAL_DECISION, "ApprovalRequest", id, "APPROVED", "{\"note\":\"" + safe(note) + "\"}");
        return req;
    }

    @PreAuthorize("hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    @Transactional
    public ApprovalRequest reject(String id, String note) {
        ApprovalRequest req = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Approval not found: " + id));
        if (req.getStatus() != ApprovalStatus.PENDING) throw new IllegalArgumentException("Approval not pending");
        String actor = SecurityContextHolder.getContext().getAuthentication().getName();
        req.reject(actor, note);
        audit.record(AuditAction.APPROVAL_DECISION, "ApprovalRequest", id, "REJECTED", "{\"note\":\"" + safe(note) + "\"}");
        return req;
    }

    private String safe(String s){ return s == null ? "" : s.replace("\"","\\\\\""); }
}
