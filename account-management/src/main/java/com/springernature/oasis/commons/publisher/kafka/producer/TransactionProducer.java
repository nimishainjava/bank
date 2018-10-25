package com.springernature.oasis.commons.publisher.kafka.producer;

import com.springernature.oasis.model.TransactionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionProducer {

    @Value("${account-management.transaction.topic}")
    private String TOPIC_TRANSCATIONS;

    @Autowired
    private KafkaTemplate<String, TransactionDetails> kafkaTemplate;

    public void publish(TransactionDetails transactionDetails) {

        try {
            kafkaTemplate.send(TOPIC_TRANSCATIONS, transactionDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
