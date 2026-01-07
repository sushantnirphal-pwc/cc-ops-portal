package com.company.ccops.txn;

import com.company.ccops.audit.AuditAction;
import com.company.ccops.audit.AuditService;
import com.company.ccops.txn.dto.TransactionDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repo;
    private final AuditService audit;

    public TransactionService(TransactionRepository repo, AuditService audit) {
        this.repo = repo;
        this.audit = audit;
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public List<TransactionDto> search(String accountId, Instant from, Instant to, String merchant) {
        audit.record(AuditAction.TXN_SEARCH, "Transaction", "-", "OK", "{\"accountId\":\"" + (accountId==null?"":accountId) + "\"}");
        return repo.search(accountId, from, to, merchant).stream()
                .map(t -> new TransactionDto(t.getId(), t.getAccountId(), t.getCardId(), t.getAmount(),
                        t.getCurrency(), t.getMcc(), t.getMerchantName(), t.getTxnTime(), t.getStatus(), t.getAuthCode()))
                .toList();
    }

    public Transaction getById(String id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + id));
    }
}
