package com.bank.transaction.service;


import com.bank.transaction.dto.CreateTransactionRequest;
import com.bank.transaction.dto.TransactionResponse;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponse createTransaction(
            CreateTransactionRequest request
    );

    TransactionResponse getTransactionById(UUID id);

    List<TransactionResponse> getTransactionsBySourceAccountIdOrDestinationAccountId(
            UUID sourceAccountId,
            UUID destinationAccountId
    );
}
