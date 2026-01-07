package com.company.ccops.audit;

import com.company.ccops.common.util.Correlation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuditService {

    private final AuditRepository repo;

    public AuditService(AuditRepository repo) {
        this.repo = repo;
    }

    public void record(AuditAction action, String entityType, String entityId, String outcome, String detailsJson) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String actor = (auth == null) ? "anonymous" : auth.getName();

        repo.save(new AuditEvent(
                Instant.now(),
                actor,
                action,
                entityType,
                entityId == null ? "-" : entityId,
                Correlation.getId() == null ? "-" : Correlation.getId(),
                outcome == null ? "OK" : outcome,
                detailsJson
        ));
    }
}
