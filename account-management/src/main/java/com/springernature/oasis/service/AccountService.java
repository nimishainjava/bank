package com.springernature.oasis.service;

import com.springernature.oasis.commons.exception.AccountException;
import com.springernature.oasis.commons.publisher.kafka.producer.TransactionProducer;
import com.springernature.oasis.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static com.springernature.oasis.constants.Messages.*;

@Service
public class AccountService {

    // MongoDB Enpoints
    private final String GET_ACCOUNT_BY_ACCOUNT_ID = "/account/{*}";

    @Autowired
    private TransactionProducer transactionProducer;

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${mongodb.url}")
    private String mongoDBServiceUrl;

    @Value("${mongodb.port}")
    private String mongoDBServicePort;

    public AccountResponse deposit(final TransactionDetails transactionDetails) {

        // TODO: validation of transaction details send as request.

        try {
            // DB service call
            Account account = restTemplate.getForObject(createDBServiceRestUrl(GET_ACCOUNT_BY_ACCOUNT_ID.replace("{*}", transactionDetails.getToAccountNumber().toString())), Account.class);

            // Check if the account is active/overdrawn to deposit money
            if (checkAccountIsActiveOrOverDrawn(account)) {
                transactionDetails.setType(TransactionType.CREDIT);
                transactionProducer.publish(transactionDetails);
            } else {
                // throw new AccountException() -> if account status like inactive/blocked
                throw new AccountException(ACCOUNT_INACTIVE_MSG, HttpStatus.FORBIDDEN);
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new AccountException(ACCOUNT_INVALID_MSG, e.getStatusCode());
            else if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                // Log Exception
                throw new AccountException(INTERNAL_SERVER_ERROR_MSG, e.getStatusCode());
        }catch (AccountException ae) {
            throw ae;
        } catch (Exception e) {
            // Log Exception
            throw new AccountException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new AccountResponse(DEPOSIT_SUCCESS_MSG.replace("{*}", transactionDetails.getAmount().toString()), HttpStatus.OK);

    }

    public AccountResponse withdraw(final TransactionDetails transactionDetails) {

        // TODO: validation of transaction details send as request.

        try {
            // DB service call
            Account account = restTemplate.getForObject(createDBServiceRestUrl(GET_ACCOUNT_BY_ACCOUNT_ID.replace("{*}", transactionDetails.getFromAccountNumber().toString())), Account.class);

            // Check if the account is active/overdrawn. Then check if the account has sufficient balance for the withdrawal request.
            if (checkAccountIsActiveOrOverDrawn(account)) {
                if (checkIfSufficientBalanceInAccount(account, transactionDetails)) {
                    transactionDetails.setType(TransactionType.DEBIT);
                    transactionProducer.publish(transactionDetails);
                } else {
                    throw new AccountException(INSUFFICIENT_BALANCE_MSG, HttpStatus.UNPROCESSABLE_ENTITY);
                }
            } else {
                // throw new AccountException() -> if account status like inactive/blocked
                throw new AccountException(ACCOUNT_INACTIVE_MSG, HttpStatus.FORBIDDEN);
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new AccountException(ACCOUNT_INVALID_MSG, e.getStatusCode());
            else if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                // Log Exception
                throw new AccountException(INTERNAL_SERVER_ERROR_MSG, e.getStatusCode());
        } catch (AccountException ae) {
            throw ae;
        } catch (Exception e) {
            // Log Exception
            throw new AccountException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new AccountResponse(WITHDRAW_SUCCESS_MSG.replace("{*}", transactionDetails.getAmount().toString()), HttpStatus.OK);
    }

    public void transfer(final TransactionDetails transactionDetails) {

        // TODO: Discuss the flow for transfer

        /*transactionDetails.setType(TransactionType.TRANSFER);
        transactionProducer.publish(transactionDetails);*/

    }

    public AccountResponse updateAccount(TransactionDetails transactionDetails, TransactionType transactionType) {

        // TODO: validate the data in request.

        try {

            Account account = restTemplate.getForObject(createDBServiceRestUrl(GET_ACCOUNT_BY_ACCOUNT_ID.replace("{*}", transactionDetails.getToAccountNumber().toString())), Account.class);

            // update account available balance
            updateAccountBalance(account, transactionType, transactionDetails);

            //update account status
            updateAccountStatus(account);

            // log transaction
            //logTranscation(account, transactionDetails);

            // TODO: send updated object to DB Service

        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND))
                throw new AccountException(ACCOUNT_INVALID_MSG, e.getStatusCode());
            else if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
                // Log Exception
                throw new AccountException(INTERNAL_SERVER_ERROR_MSG, e.getStatusCode());
        } catch (Exception e) {
            // Log Exception
            throw new AccountException(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new AccountResponse("Success.!!", HttpStatus.OK);

    }

    private void updateAccountBalance(Account account, TransactionType transactionType, TransactionDetails transactionDetails) {
        if (transactionType.equals(TransactionType.CREDIT)) {
            account.setBalance(account.getBalance().add(transactionDetails.getAmount()));
        } else {
            account.setBalance(account.getBalance().subtract(transactionDetails.getAmount()));
        }
    }

    private void updateAccountStatus(Account account) {
        if (account.getBalance().doubleValue() < 0.00 && account.getStatus().equals(AccountStatusType.ACTIVE)) {
            account.setStatus(AccountStatusType.OVERDRAWN);
        } else if (account.getBalance().doubleValue() >= 0.00 && account.getStatus().equals(AccountStatusType.OVERDRAWN)) {
            account.setStatus(AccountStatusType.ACTIVE);
        }
    }

    private void logTransaction(Account account, TransactionDetails transactionDetails) {
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

    private String createDBServiceRestUrl(final String endpoint) {
        return getDBServiceURL() + endpoint;
    }

    private String getDBServiceURL() {
        return mongoDBServiceUrl + ":" + mongoDBServicePort;
    }

    private boolean checkAccountIsActiveOrOverDrawn(Account account) {
        return ( account.getStatus().equals(AccountStatusType.ACTIVE)
                || account.getStatus().equals(AccountStatusType.OVERDRAWN) );
    }

    private boolean checkIfSufficientBalanceInAccount(Account account, TransactionDetails transactionDetails) {
        return (account.getBalance().doubleValue() + account.getOverDrawnLimit().doubleValue()) >= transactionDetails.getAmount().doubleValue();
    }

}
