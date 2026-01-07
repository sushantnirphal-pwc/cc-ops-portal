package com.company.ccops.txn.dto;

import com.company.ccops.txn.TxnStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionDto(
        String id,
        String accountId,
        String cardId,
        BigDecimal amount,
        String currency,
        String mcc,
        String merchantName,
        Instant txnTime,
        TxnStatus status,
        String authCode
) {}
