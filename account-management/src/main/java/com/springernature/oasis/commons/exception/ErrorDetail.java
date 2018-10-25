package com.springernature.oasis.commons.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ErrorDetail implements Serializable {

    private Long timeStamp;
    private HttpStatus status;
    private String message;

    public ErrorDetail() {
        status = HttpStatus.OK;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}