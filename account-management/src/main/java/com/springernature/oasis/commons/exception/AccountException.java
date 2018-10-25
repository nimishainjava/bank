package com.springernature.oasis.commons.exception;

import org.springframework.http.HttpStatus;

public class AccountException extends RuntimeException {

    private HttpStatus httpStatus;

    public AccountException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
