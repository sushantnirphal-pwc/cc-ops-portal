package com.company.ccops.dispute.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateDisputeRequest(
        @NotBlank String customerId,
        @NotBlank String accountId,
        @NotBlank String txnId,
        @NotBlank String reasonCode,
        @NotNull BigDecimal amount
) {}
