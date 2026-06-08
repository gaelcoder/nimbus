package com.bank.account.service;

import com.bank.account.dto.AccountResponse;
import com.bank.account.dto.CreateAccountRequest;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponse createAccount(
            CreateAccountRequest request
    );

    AccountResponse getAccountById(UUID id);

    List<AccountResponse> getAccountsByUserId(UUID userId);
}
