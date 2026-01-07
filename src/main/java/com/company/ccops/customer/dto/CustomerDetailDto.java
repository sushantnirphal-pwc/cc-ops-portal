package com.company.ccops.customer.dto;

import java.time.Instant;

public record CustomerDetailDto(
        String id,
        String fullName,
        String emailMasked,
        String phoneMasked,
        String kycStatus,
        String riskTier,
        Instant createdAt
) {}
