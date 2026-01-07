package com.company.ccops.dispute;

import com.company.ccops.audit.AuditAction;
import com.company.ccops.audit.AuditService;
import com.company.ccops.dispute.dto.CreateDisputeRequest;
import com.company.ccops.txn.TransactionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DisputeService {

    private final DisputeRepository repo;
    private final TransactionService txnService;
    private final AuditService audit;

    public DisputeService(DisputeRepository repo, TransactionService txnService, AuditService audit) {
        this.repo = repo;
        this.txnService = txnService;
        this.audit = audit;
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public Dispute create(CreateDisputeRequest req) {
        txnService.getById(req.txnId());

        Dispute d = repo.save(new Dispute(
                req.customerId(),
                req.accountId(),
                req.txnId(),
                req.reasonCode(),
                DisputeStatus.OPEN,
                req.amount(),
                Instant.now()
        ));
        audit.record(AuditAction.DISPUTE_CREATED, "Dispute", d.getId(), "OK", "{\"txnId\":\"" + req.txnId() + "\"}");
        return d;
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public Dispute get(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Dispute not found: " + id));
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public List<Dispute> listByCustomer(String customerId) {
        return repo.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }
}
