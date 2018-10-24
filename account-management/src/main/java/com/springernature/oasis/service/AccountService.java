package com.springernature.oasis.service;

import com.springernature.oasis.commons.publisher.kafka.producer.TransactionProducer;
import com.springernature.oasis.model.TransactionDetails;
import com.springernature.oasis.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private TransactionProducer transactionProducer;

    public void deposit(final TransactionDetails transactionDetails) {
        // TODO: validation of account and retrieved info
        transactionDetails.setType(TransactionType.CREDIT);
        transactionProducer.publish(transactionDetails);
    }

    public void withdraw(final TransactionDetails transactionDetails) {
        // TODO: validation of account and retrieved info
        transactionDetails.setType(TransactionType.DEBIT);
        transactionProducer.publish(transactionDetails);
    }

    public void transfer(final TransactionDetails transactionDetails) {
        transactionDetails.setType(TransactionType.TRANSFER);
        transactionProducer.publish(transactionDetails);
    }



}
