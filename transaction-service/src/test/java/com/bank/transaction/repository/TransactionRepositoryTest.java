package com.bank.transaction.repository;

import com.bank.transaction.entity.Transaction;
import com.bank.transaction.enums.TransactionStatus;
import com.bank.transaction.enums.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void shouldFindTransactionsByAccount() {

        UUID accountId = UUID.randomUUID();

        Transaction transaction =
                Transaction.builder()
                        .sourceAccountId(accountId)
                        .destinationAccountId(UUID.randomUUID())
                        .amount(BigDecimal.TEN)
                        .type(TransactionType.TRANSFER)
                        .status(TransactionStatus.COMPLETED)
                        .build();

        transactionRepository.save(transaction);

        List<Transaction> result =
                transactionRepository
                        .findAllBySourceAccountIdOrDestinationAccountId(
                                accountId,
                                accountId
                        );

        assertFalse(result.isEmpty());
    }
}