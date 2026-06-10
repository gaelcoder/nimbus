package com.bank.transaction.aws.sqs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
@RequiredArgsConstructor
public class SqsProducer {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.transaction-queue-url}")
    private String queueUrl;

    public void sendMessage(String message) {

        SendMessageRequest request =
                SendMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .messageBody(message)
                        .build();

        sqsClient.sendMessage(request);
    }
}