package com.springernature.oasis.commons.exception;

import com.springernature.oasis.model.AccountResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<Object> handleAllOther(AccountException ex){
        return new AccountResponse(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<Object> handleHttpException(HttpStatusCodeException ex){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setMessage(ex.getMessage());
        errorDetail.setStatus(ex.getStatusCode());
        return new ResponseEntity<>(errorDetail, errorDetail.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setMessage(ex.getConstraintViolations().
                stream().map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()).stream().map(Object::toString)
                .collect(Collectors.joining(", ")));
        errorDetail.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetail, errorDetail.getStatus());
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Object> handleResourceAccessException(ResourceAccessException ex){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setMessage(ex.getMessage());
        errorDetail.setStatus(HttpStatus.BAD_GATEWAY);
        return new ResponseEntity<>(errorDetail, errorDetail.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimeStamp(new Date().getTime());
        errorDetail.setMessage(getFeildErrorMessage(ex));
        errorDetail.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetail, errorDetail.getStatus());
    }

    private String getFeildErrorMessage(MethodArgumentNotValidException ex) {
        String errorMessages = "";
        for (ObjectError fieldError: ex.getBindingResult().getAllErrors()){
            errorMessages = errorMessages + fieldError.getDefaultMessage() + " ";
        }
        return errorMessages;
    }

}
