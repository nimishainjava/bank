package com.springernature.oasis.controller;


import org.camunda.bpm.engine.ProcessEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionRestController {
  
  @GetMapping(value = "/test")
  public ResponseEntity<Object> getContractDetails(@PathVariable("id") String id) throws Exception {
    return new ResponseEntity(id,HttpStatus.OK);
  }
}
