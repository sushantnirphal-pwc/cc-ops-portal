package com.company.ccops.customer.dto;

public record CustomerSummaryDto(
        String id,
        String fullName,
        String emailMasked,
        String phoneMasked,
        String kycStatus,
        String riskTier
) {}
