package com.bank.transaction.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
public class CreateTransactionRequest {

    @NotNull
    private UUID sourceAccountId;

    @NotNull
    private UUID destinationAccountId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
}
