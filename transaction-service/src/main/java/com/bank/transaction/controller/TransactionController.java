package com.bank.transaction.controller;

import com.bank.transaction.dto.CreateTransactionRequest;
import com.bank.transaction.dto.TransactionResponse;
import com.bank.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody CreateTransactionRequest request
    ) {

        return ResponseEntity.ok(
                transactionService.createTransaction(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                transactionService.getTransactionById(id)
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionResponse>>
    getTransactionHistory(
            @RequestParam UUID sourceAccountId,
            @RequestParam UUID destinationAccountId
    ) {

        return ResponseEntity.ok(
                transactionService
                        .getTransactionsBySourceAccountIdOrDestinationAccountId(
                                sourceAccountId,
                                destinationAccountId
                        )
        );
    }
}