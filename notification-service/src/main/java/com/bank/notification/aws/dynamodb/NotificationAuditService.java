package com.bank.notification.aws.dynamodb;

import com.bank.notification.aws.dynamodb.entity.NotificationAudit;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.time.Instant;
import java.util.Map;

@Service
public class NotificationAuditService {

    private final DynamoDbClient dynamoDbClient;

    public NotificationAuditService(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void save(NotificationAudit audit) {

        Map<String, AttributeValue> item = Map.of(
                "transactionId", AttributeValue.builder().s(audit.getTransactionId().toString()).build(),
                "sourceAccountId", AttributeValue.builder().s(audit.getSourceAccountId().toString()).build(),
                "destinationAccountId", AttributeValue.builder().s(audit.getDestinationAccountId().toString()).build(),
                "amount", AttributeValue.builder().n(audit.getAmount().toString()).build(),
                "status", AttributeValue.builder().s(audit.getStatus()).build(),
                "timestamp", AttributeValue.builder().s(Instant.now().toString()).build()
        );

        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName("nimbus-audit")
                .item(item)
                .build());
    }
}