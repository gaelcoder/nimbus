package com.bank.transaction.service;

import com.bank.transaction.dto.CreateTransactionRequest;
import com.bank.transaction.dto.TransactionResponse;
import com.bank.transaction.entity.Transaction;
import com.bank.transaction.enums.TransactionStatus;
import com.bank.transaction.enums.TransactionType;
import com.bank.transaction.exception.TransactionNotFoundException;
import com.bank.transaction.repository.TransactionRepository;
import com.bank.transaction.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void shouldCreateTransaction() {

        UUID sourceId = UUID.randomUUID();
        UUID destinationId = UUID.randomUUID();

        CreateTransactionRequest request =
                CreateTransactionRequest.builder()
                        .sourceAccountId(sourceId)
                        .destinationAccountId(destinationId)
                        .amount(BigDecimal.valueOf(100))
                        .build();

        Transaction transaction =
                Transaction.builder()
                        .id(UUID.randomUUID())
                        .sourceAccountId(sourceId)
                        .destinationAccountId(destinationId)
                        .amount(BigDecimal.valueOf(100))
                        .type(TransactionType.TRANSFER)
                        .status(TransactionStatus.COMPLETED)
                        .build();

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        TransactionResponse response =
                transactionService.createTransaction(request);

        assertNotNull(response);

        verify(transactionRepository)
                .save(any(Transaction.class));
    }

    @Test
    void shouldFindTransactionById() {

        UUID id = UUID.randomUUID();

        Transaction transaction =
                Transaction.builder()
                        .id(id)
                        .sourceAccountId(UUID.randomUUID())
                        .destinationAccountId(UUID.randomUUID())
                        .amount(BigDecimal.TEN)
                        .type(TransactionType.TRANSFER)
                        .status(TransactionStatus.COMPLETED)
                        .build();

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(transaction));

        TransactionResponse response =
                transactionService.getTransactionById(id);

        assertEquals(id, response.getId());
    }

    @Test
    void shouldThrowWhenTransactionNotFound() {

        UUID id = UUID.randomUUID();

        when(transactionRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                TransactionNotFoundException.class,
                () -> transactionService.getTransactionById(id)
        );
    }

    @Test
    void shouldReturnHistory() {

        UUID accountId = UUID.randomUUID();

        Transaction transaction =
                Transaction.builder()
                        .id(UUID.randomUUID())
                        .sourceAccountId(accountId)
                        .destinationAccountId(UUID.randomUUID())
                        .amount(BigDecimal.TEN)
                        .type(TransactionType.TRANSFER)
                        .status(TransactionStatus.COMPLETED)
                        .build();

        when(
                transactionRepository
                        .findAllBySourceAccountIdOrDestinationAccountId(
                                accountId,
                                accountId
                        )
        ).thenReturn(List.of(transaction));

        List<TransactionResponse> responses =
                transactionService
                        .getTransactionsBySourceAccountIdOrDestinationAccountId(
                                accountId,
                                accountId
                        );

        assertEquals(1, responses.size());
    }
}