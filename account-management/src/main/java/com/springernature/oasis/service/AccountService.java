package com.springernature.oasis.service;

import com.springernature.oasis.commons.exception.AccountException;
import com.springernature.oasis.commons.publisher.kafka.producer.TransactionProducer;
import com.springernature.oasis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Date;

@Service
public class AccountService {

    private String DEPOST_SUCCESS_MESSAGE = "Amount INR {*} has been credited successfully.";

    @Autowired
    private TransactionProducer transactionProducer;

    public AccountResponse deposit(final TransactionDetails transactionDetails) {

        // TODO: validation of account and retrieved info

        try {
            // DB service call

            // throw new AccountException() -> if account status like inactive/blocked
        } catch (HttpStatusCodeException e) {
            throw (new AccountException(e.getMessage(), e.getStatusCode()));
        }

        transactionDetails.setType(TransactionType.CREDIT);
        transactionProducer.publish(transactionDetails);

        return new AccountResponse(DEPOST_SUCCESS_MESSAGE.replace("{*}", transactionDetails.getAmount().toString()), HttpStatus.OK);

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

    public void updateAccount(TransactionDetails transactionDetails) {

        // TODO: fetch account details from DB

        Account account = new Account();

        // update account available balance
        updateAccountBalance(account, transactionDetails);

        //update account status
        updateAccountStatus(account, transactionDetails);

        // log transaction
        logTranscation(account, transactionDetails);

        // TODO: send updated object to DB Service


    }

    private void updateAccountBalance(Account account, TransactionDetails transactionDetails) {
        if (transactionDetails.getType().equals(TransactionType.CREDIT)) {
            account.setBalance(account.getBalance().add(transactionDetails.getAmount()));
        } else {
            account.setBalance(account.getBalance().subtract(transactionDetails.getAmount()));
        }
    }

    private void updateAccountStatus(Account account, TransactionDetails transactionDetails) {
        if (account.getBalance().doubleValue() < 0.00 && account.getStatus().equals(AccountStatusType.ACTIVE)) {
            account.setStatus(AccountStatusType.OVERDRAWN);
        } else if (account.getBalance().doubleValue() >= 0.00 && account.getStatus().equals(AccountStatusType.OVERDRAWN)) {
            account.setStatus(AccountStatusType.ACTIVE);
        }
    }

    private void logTranscation(Account account, TransactionDetails transactionDetails) {
        account.getTransactions().add(getTransaction(transactionDetails));
    }

    private Transaction getTransaction(TransactionDetails transactionDetails) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setDescription(getTransactionDescription(transactionDetails));
        transaction.setType(transactionDetails.getType().toString());
        transaction.setDate(new Date());
        return transaction;
    }

    private String getTransactionDescription(TransactionDetails transactionDetails) {
        if (transactionDetails.getType().equals(TransactionType.CREDIT)) {
            return "From Self/" + transactionDetails.getDescription();
        } else if (transactionDetails.getType().equals(TransactionType.DEBIT)) {
            return "To Self/" + transactionDetails.getDescription();
        } else {
            return transactionDetails.getFromAccountNumber() + "/" + transactionDetails.getDescription() + "/" + transactionDetails.getToAccountNumber();
        }
    }

}
