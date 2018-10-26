package com.springernature.oasis.controller;

import com.springernature.oasis.model.TransactionDetails;
import com.springernature.oasis.model.TransactionType;
import com.springernature.oasis.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class AccountTransactionController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/deposit")
    public ResponseEntity deposit(@RequestBody TransactionDetails transactionDetails) {
        return accountService.updateAccount(transactionDetails, TransactionType.CREDIT);
    }

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@RequestBody TransactionDetails transactionDetails) {
        return accountService.updateAccount(transactionDetails, TransactionType.DEBIT);
    }

    @PostMapping("/transfer")
    public ResponseEntity transfer(@RequestBody TransactionDetails transactionDetails) {
        return accountService.updateAccount(transactionDetails, TransactionType.TRANSFER);
    }

}
