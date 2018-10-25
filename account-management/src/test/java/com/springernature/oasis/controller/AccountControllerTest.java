package com.springernature.oasis.controller;

import com.springernature.oasis.App;
import com.springernature.oasis.model.TransactionDetails;
import com.springernature.oasis.model.TransactionType;
import com.sun.corba.se.impl.naming.cosnaming.TransientNameServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate;
    HttpHeaders headers;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Before
    public  void setUp() {
        testRestTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void shouldDepositAmountInAccountTest() {

        BigDecimal depositAmount = new BigDecimal(11.00);

        TransactionDetails request = new TransactionDetails();
        request.setAmount(depositAmount);
        request.setFromAccountNumber(1000L);
        request.setDescription("Deposited Amount");

        HttpEntity<TransactionDetails> entity = new HttpEntity<>(request, headers);

        ResponseEntity response = testRestTemplate.exchange(createURLWithPort("/account/deposit"), HttpMethod.POST, entity, String.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue(response.getBody().toString().contains("Amount INR " + depositAmount + " has been credited successfully."));

    }

    @Test
    public void shouldWithdrawAmountFromAccountTest() {

    }
}