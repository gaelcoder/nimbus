package com.bank.account.controller;

import com.bank.account.dto.AccountResponse;
import com.bank.account.dto.CreateAccountRequest;
import com.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @RequestBody CreateAccountRequest request
    ) {

        return ResponseEntity.ok(
                accountService.createAccount(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                accountService.getAccountById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByUserId(
            @PathVariable UUID userId
    ) {

        return ResponseEntity.ok(
                accountService.getAccountsByUserId(userId)
        );
    }
}