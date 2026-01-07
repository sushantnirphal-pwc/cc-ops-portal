package com.company.ccops.approval.dto;

import jakarta.validation.constraints.NotBlank;

public record DecisionRequest(
        @NotBlank String note
) {}
