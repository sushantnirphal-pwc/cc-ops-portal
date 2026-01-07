package com.company.ccops.card.dto;

import com.company.ccops.card.BlockReason;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BlockCardRequestDto(
        @NotNull BlockReason reason,
        @NotBlank String note
) {}
