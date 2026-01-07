package com.company.ccops.card.dto;

import com.company.ccops.card.CardStatus;

public record CardDto(
        String id,
        String accountId,
        String cardToken,
        String last4,
        CardStatus status,
        String embossName,
        int expiryMonth,
        int expiryYear
) {}
