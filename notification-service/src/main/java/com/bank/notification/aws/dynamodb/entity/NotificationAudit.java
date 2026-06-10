package com.bank.notification.aws.dynamodb.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationAudit {

    private UUID transactionId;
    private UUID sourceAccountId;
    private UUID destinationAccountId;
    private BigDecimal amount;
    private Instant timestamp;
    private String status;
}