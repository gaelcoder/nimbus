package com.bank.transaction.service.impl;
import com.bank.shared.event.TransactionEvent;
import com.bank.transaction.aws.sqs.SqsProducer;
import com.bank.transaction.dto.CreateTransactionRequest;
import com.bank.transaction.dto.TransactionResponse;
import com.bank.transaction.entity.Transaction;
import com.bank.transaction.enums.TransactionStatus;
import com.bank.transaction.enums.TransactionType;
import com.bank.transaction.exception.TransactionNotFoundException;
import com.bank.transaction.repository.TransactionRepository;
import com.bank.transaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final SqsProducer sqsProducer;
    private final ObjectMapper objectMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionResponse createTransaction(
            CreateTransactionRequest request
    ) {

        Transaction transaction = Transaction.builder()
                .sourceAccountId(request.getSourceAccountId())
                .destinationAccountId(request.getDestinationAccountId())
                .amount(request.getAmount())
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.COMPLETED)
                .build();

        Transaction saved =
                transactionRepository.save(transaction);

        publishTransactionEvent(saved);

        return mapToResponse(saved);
    }

    private TransactionResponse mapToResponse(
            Transaction transaction
    ) {

        return TransactionResponse.builder()
                .id(transaction.getId())
                .sourceAccountId(transaction.getSourceAccountId())
                .destinationAccountId(transaction.getDestinationAccountId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .status(transaction.getStatus())
                .build();
    }

    @Override
    public TransactionResponse getTransactionById(UUID id) {

        Transaction transaction =
                transactionRepository.findById(id)
                        .orElseThrow(() ->
                                new TransactionNotFoundException(
                                        "Transaction not found"
                                ));

        return mapToResponse(transaction);
    }

    @Override
    public List<TransactionResponse>
    getTransactionsBySourceAccountIdOrDestinationAccountId(
            UUID sourceAccountId,
            UUID destinationAccountId
    ) {

        return transactionRepository
                .findAllBySourceAccountIdOrDestinationAccountId(
                        sourceAccountId,
                        destinationAccountId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void publishTransactionEvent(Transaction transaction) {

        try {

            TransactionEvent event =
                    TransactionEvent.builder()
                            .transactionId(transaction.getId())
                            .sourceAccountId(transaction.getSourceAccountId())
                            .destinationAccountId(transaction.getDestinationAccountId())
                            .amount(transaction.getAmount())
                            .build();

            String message =
                    objectMapper.writeValueAsString(event);

            sqsProducer.sendMessage(message);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to publish transaction event",
                    e
            );
        }
    }


}
