package com.bank.account.service.impl;

import com.bank.account.dto.AccountResponse;
import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.entity.Account;
import com.bank.account.enums.AccountStatus;
import com.bank.account.repository.AccountRepository;
import com.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public AccountResponse createAccount(CreateAccountRequest request) {

        Account account = Account.builder()
                .userId(request.getUserId())
                .accountNumber(generateAccountNumber())
                .agency("0001")
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .build();

        Account savedAccount = accountRepository.save(account);

        return mapToResponse(savedAccount);
    }

    @Override
    public AccountResponse getAccountById(UUID id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Conta não encontrada"));

        return mapToResponse(account);
    }

    private String generateAccountNumber() {
        return String.valueOf(
                System.currentTimeMillis()
        );
    }

    private AccountResponse mapToResponse(Account account) {

        return AccountResponse.builder()
                .id(account.getId())
                .userId(account.getUserId())
                .accountNumber(account.getAccountNumber())
                .agency(account.getAgency())
                .balance(account.getBalance())
                .status(account.getStatus())
                .build();
    }

    @Override
    public List<AccountResponse> getAccountsByUserId(UUID userId) {

        return accountRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}