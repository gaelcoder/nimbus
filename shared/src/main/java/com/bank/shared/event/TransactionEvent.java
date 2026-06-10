package com.bank.shared.event;

import java.util.UUID;
import java.math.BigDecimal;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {

    private UUID transactionId;

    private UUID sourceAccountId;

    private UUID destinationAccountId;

    private BigDecimal amount;
}