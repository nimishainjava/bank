package com.springernature.oasis.controller;

import com.springernature.oasis.model.TransactionDetails;
import com.springernature.oasis.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity desposit(@Valid @RequestBody TransactionDetails request) {
        return accountService.deposit(request);
    }


    @PostMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity withdraw(@RequestBody TransactionDetails request) {

        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transfer(@RequestBody TransactionDetails request) {

        return new ResponseEntity(HttpStatus.OK);
    }

}
