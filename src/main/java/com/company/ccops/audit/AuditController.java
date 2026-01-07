package com.company.ccops.audit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit")
public class AuditController {

    private final AuditRepository repo;

    public AuditController(AuditRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    @PreAuthorize("hasRole('AUDITOR') or hasRole('ADMIN')")
    public Page<AuditEvent> getByEntity(@RequestParam String entityType,
                                        @RequestParam String entityId,
                                        Pageable pageable) {
        return repo.findByEntityTypeAndEntityIdOrderByTimestampDesc(entityType, entityId, pageable);
    }
}
