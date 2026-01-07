package com.company.ccops.account;

import com.company.ccops.audit.AuditAction;
import com.company.ccops.audit.AuditService;
import com.company.ccops.account.dto.AccountDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository repo;
    private final AuditService audit;

    public AccountService(AccountRepository repo, AuditService audit) {
        this.repo = repo;
        this.audit = audit;
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public List<AccountDto> listByCustomer(String customerId) {
        audit.record(AuditAction.ACCOUNT_VIEW, "Customer", customerId, "OK", "{\"listAccounts\":true}");
        return repo.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(a -> new AccountDto(a.getId(), a.getCustomerId(), a.getProductType(), a.getCreditLimit(),
                        a.getAvailableLimit(), a.getStatus(), a.getBillingDay(), a.getCreatedAt()))
                .toList();
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public AccountDto get(String id) {
        Account a = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));
        audit.record(AuditAction.ACCOUNT_VIEW, "Account", id, "OK", null);
        return new AccountDto(a.getId(), a.getCustomerId(), a.getProductType(), a.getCreditLimit(),
                a.getAvailableLimit(), a.getStatus(), a.getBillingDay(), a.getCreatedAt());
    }
}
