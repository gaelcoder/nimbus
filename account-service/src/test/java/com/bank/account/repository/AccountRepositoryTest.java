package com.bank.account.repository;

import com.bank.account.entity.Account;
import com.bank.account.enums.AccountStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldFindAccountByUserId() {

        UUID userId = UUID.randomUUID();

        Account account =
                Account.builder()
                        .userId(userId)
                        .accountNumber("000001")
                        .agency("0001")
                        .balance(BigDecimal.ZERO)
                        .status(AccountStatus.ACTIVE)
                        .build();

        accountRepository.save(account);

        List<Account> result =
                accountRepository.findByUserId(userId);

        assertFalse(result.isEmpty());
    }
}