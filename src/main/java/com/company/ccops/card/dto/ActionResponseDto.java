package com.company.ccops.card.dto;

public record ActionResponseDto(
        String status,
        String message,
        String approvalId,
        String blockRequestId
) {}
