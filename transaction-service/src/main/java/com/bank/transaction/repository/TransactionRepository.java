package com.bank.transaction.repository;

import com.bank.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>  {

    List<Transaction> findAllBySourceAccountIdOrDestinationAccountId(
            UUID sourceAccountId,
            UUID destinationAccountId
    );

    }
