package com.springernature.oasis.exception;


import com.springernature.oasis.model.ErrorDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(MongoDbServiceException.class)
    public ResponseEntity<Object> handleAllExceptions(MongoDbServiceException ex){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setMessage(ex.getMessage());
        errorDetail.setStatus(ex.getHttpStatus());
        return new ResponseEntity<>(errorDetail, errorDetail.getStatus());
    }

}
