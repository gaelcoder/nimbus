package com.bank.transaction.dto;
import com.bank.transaction.enums.TransactionStatus;
import com.bank.transaction.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TransactionResponse {

    private UUID id;

    private UUID sourceAccountId;

    private UUID destinationAccountId;

    private BigDecimal amount;

    private TransactionType type;

    private TransactionStatus status;
}
