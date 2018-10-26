package com.springernature.oasis.controller;

import com.springernature.oasis.App;
import com.springernature.oasis.model.TransactionDetails;
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

import static com.springernature.oasis.constants.Messages.*;

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
        request.setToAccountNumber(1L);
        request.setDescription("Deposited Amount");

        HttpEntity<TransactionDetails> entity = new HttpEntity<>(request, headers);

        ResponseEntity response = testRestTemplate.exchange(createURLWithPort("/account/deposit"), HttpMethod.POST, entity, String.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue(response.getBody().toString().contains(DEPOSIT_SUCCESS_MSG.replace("{*}", depositAmount.toString())));

    }

    @Test
    public void shouldDepositFailIfAccountNumberIsInvalid() {

        BigDecimal depositAmount = new BigDecimal(11.00);

        TransactionDetails request = new TransactionDetails();
        request.setAmount(depositAmount);
        request.setToAccountNumber(324786L);
        request.setDescription("Deposited Amount");

        HttpEntity<TransactionDetails> entity = new HttpEntity<>(request, headers);

        ResponseEntity response = testRestTemplate.exchange(createURLWithPort("/account/deposit"), HttpMethod.POST, entity, String.class);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertTrue(response.getBody().toString().contains(ACCOUNT_INVALID_MSG));

    }

    @Test
    public void shouldDepositFailIfAccountStatusIsBlocked() {

        BigDecimal depositAmount = new BigDecimal(11.00);

        TransactionDetails request = new TransactionDetails();
        request.setAmount(depositAmount);
        request.setToAccountNumber(2L);
        request.setDescription("Deposited Amount");

        HttpEntity<TransactionDetails> entity = new HttpEntity<>(request, headers);

        ResponseEntity response = testRestTemplate.exchange(createURLWithPort("/account/deposit"), HttpMethod.POST, entity, String.class);

        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        Assert.assertTrue(response.getBody().toString().contains(ACCOUNT_INACTIVE_MSG));

    }


    @Test
    public void shouldWithdrawFailWhenInSufficientFundsInAccount() {

        BigDecimal depositAmount = new BigDecimal(7234.00);

        TransactionDetails request = new TransactionDetails();
        request.setAmount(depositAmount);
        request.setFromAccountNumber(1L);
        request.setDescription("Withdraw Amount");

        HttpEntity<TransactionDetails> entity = new HttpEntity<>(request, headers);

        ResponseEntity response = testRestTemplate.exchange(createURLWithPort("/account/withdraw"), HttpMethod.POST, entity, String.class);

        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        Assert.assertTrue(response.getBody().toString().contains(INSUFFICIENT_BALANCE_MSG));

    }




}