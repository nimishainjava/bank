package com.springernature.oasis.controller;

import com.springernature.oasis.model.CustomerDetails;
import com.springernature.oasis.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/create")
    public ResponseEntity createCustomer(@RequestBody CustomerDetails customerDetail){
        customerService.create(customerDetail);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @GetMapping(value = "/account/{accounId}")
    public ResponseEntity getAccount(@PathVariable("accounId") Long accountId) {
        try {
            return new ResponseEntity(customerService.getAccount(accountId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Not Found", HttpStatus.NOT_FOUND);
        }
    }

}
