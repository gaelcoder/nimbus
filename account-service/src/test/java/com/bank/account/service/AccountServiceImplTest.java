package com.bank.account.service;

import com.bank.account.dto.AccountResponse;
import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.entity.Account;
import com.bank.account.enums.AccountStatus;
import com.bank.account.exception.AccountNotFoundException;
import com.bank.account.repository.AccountRepository;
import com.bank.account.service.impl.AccountServiceImpl;
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
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void shouldCreateAccount() {

        UUID userId = UUID.randomUUID();

        CreateAccountRequest request =
                CreateAccountRequest.builder()
                        .userId(userId)
                        .build();

        Account account =
                Account.builder()
                        .id(UUID.randomUUID())
                        .userId(userId)
                        .accountNumber("000001")
                        .agency("0001")
                        .balance(BigDecimal.ZERO)
                        .status(AccountStatus.ACTIVE)
                        .build();

        when(accountRepository.save(any(Account.class)))
                .thenReturn(account);

        AccountResponse response =
                accountService.createAccount(request);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldFindAccountById() {

        UUID accountId = UUID.randomUUID();

        Account account =
                Account.builder()
                        .id(accountId)
                        .userId(UUID.randomUUID())
                        .accountNumber("000001")
                        .agency("0001")
                        .balance(BigDecimal.ZERO)
                        .status(AccountStatus.ACTIVE)
                        .build();

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        AccountResponse response =
                accountService.getAccountById(accountId);

        assertEquals(accountId, response.getId());
    }

    @Test
    void shouldThrowWhenAccountNotFound() {

        UUID accountId = UUID.randomUUID();

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        assertThrows(
                AccountNotFoundException.class,
                () -> accountService.getAccountById(accountId)
        );
    }

    @Test
    void shouldFindAccountsByUserId() {

        UUID userId = UUID.randomUUID();

        Account account =
                Account.builder()
                        .id(UUID.randomUUID())
                        .userId(userId)
                        .accountNumber("000001")
                        .agency("0001")
                        .balance(BigDecimal.ZERO)
                        .status(AccountStatus.ACTIVE)
                        .build();

        when(accountRepository.findByUserId(userId))
                .thenReturn(List.of(account));

        List<AccountResponse> result =
                accountService.getAccountsByUserId(userId);

        assertEquals(1, result.size());
    }
}