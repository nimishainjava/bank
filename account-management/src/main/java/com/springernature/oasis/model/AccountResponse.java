package com.springernature.oasis.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AccountResponse extends ResponseEntity {

    public AccountResponse(String statusMessage, HttpStatus status) {
        super(statusMessage, status);
    }

}
