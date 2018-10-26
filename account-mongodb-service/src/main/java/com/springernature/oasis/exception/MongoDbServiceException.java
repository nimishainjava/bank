package com.springernature.oasis.exception;

import org.springframework.http.HttpStatus;


public class MongoDbServiceException extends RuntimeException{

    private HttpStatus httpStatus;

    public MongoDbServiceException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public MongoDbServiceException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {

        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
