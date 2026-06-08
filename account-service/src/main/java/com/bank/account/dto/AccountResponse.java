package com.bank.account.dto;

import com.bank.account.enums.AccountStatus;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private UUID id;

    private UUID userId;

    private String accountNumber;

    private String agency;

    private BigDecimal balance;

    private AccountStatus status;
}