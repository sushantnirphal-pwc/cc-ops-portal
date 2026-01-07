package com.company.ccops.customer;

import com.company.ccops.audit.AuditAction;
import com.company.ccops.audit.AuditService;
import com.company.ccops.common.util.Masking;
import com.company.ccops.customer.dto.CustomerDetailDto;
import com.company.ccops.customer.dto.CustomerSummaryDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repo;
    private final AuditService audit;

    public CustomerService(CustomerRepository repo, AuditService audit) {
        this.repo = repo;
        this.audit = audit;
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public List<CustomerSummaryDto> search(String query) {
        audit.record(AuditAction.CUSTOMER_SEARCH, "Customer", "-", "OK", "{\"query\":\"" + safe(query) + "\"}");
        return repo.search(query == null ? "" : query).stream()
                .map(c -> new CustomerSummaryDto(
                        c.getId(),
                        c.getFullName(),
                        Masking.maskEmail(c.getEmail()),
                        Masking.maskPhone(c.getPhone()),
                        c.getKycStatus(),
                        c.getRiskTier()
                )).toList();
    }

    @PreAuthorize("hasRole('OPS_AGENT') or hasRole('OPS_SUPERVISOR') or hasRole('ADMIN')")
    public CustomerDetailDto get(String id) {
        Customer c = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
        audit.record(AuditAction.CUSTOMER_VIEW, "Customer", id, "OK", null);
        return new CustomerDetailDto(
                c.getId(),
                c.getFullName(),
                Masking.maskEmail(c.getEmail()),
                Masking.maskPhone(c.getPhone()),
                c.getKycStatus(),
                c.getRiskTier(),
                c.getCreatedAt()
        );
    }

    private String safe(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\\\"");
    }
}
