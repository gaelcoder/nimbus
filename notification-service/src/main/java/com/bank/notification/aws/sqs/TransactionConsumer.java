package com.bank.notification.aws.sqs;

import com.bank.notification.aws.dynamodb.NotificationAuditService;
import com.bank.notification.aws.dynamodb.entity.NotificationAudit;
import com.bank.notification.dto.NotificationRequest;
import com.bank.notification.enums.NotificationType;
import com.bank.notification.service.NotificationService;
import com.bank.shared.event.TransactionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;
    private final NotificationAuditService auditService;

    @SqsListener("${aws.sqs.transaction-queue-url}")
    public void consume(String message) {

        try {
            TransactionEvent event =
                    objectMapper.readValue(message, TransactionEvent.class);

            NotificationRequest request =
                    NotificationRequest.builder()
                            .userId(event.getSourceAccountId())
                            .destination("SYSTEM")
                            .message(buildMessage(event))
                            .type(NotificationType.TRANSACTION)
                            .build();

            notificationService.sendNotification(request);

            auditService.save(
                    NotificationAudit.builder()
                            .transactionId(event.getTransactionId())
                            .sourceAccountId(event.getSourceAccountId())
                            .destinationAccountId(event.getDestinationAccountId())
                            .amount(event.getAmount())
                            .status("PROCESSED")
                            .build()
            );

        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem SQS: " + message);
            e.printStackTrace();
        }
    }

    private String buildMessage(TransactionEvent event) {
        return "Transação " + event.getTransactionId()
                + " valor: " + event.getAmount()
                + " destino: " + event.getDestinationAccountId();
    }
}