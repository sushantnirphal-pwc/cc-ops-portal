package com.company.ccops.account.dto;

import com.company.ccops.account.AccountStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountDto(
        String id,
        String customerId,
        String productType,
        BigDecimal creditLimit,
        BigDecimal availableLimit,
        AccountStatus status,
        int billingDay,
        Instant createdAt
) {}
