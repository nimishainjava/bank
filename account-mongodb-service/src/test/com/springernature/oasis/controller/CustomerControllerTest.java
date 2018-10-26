package com.springernature.oasis.controller;

import com.springernature.oasis.Application;
import com.springernature.oasis.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
public class CustomerControllerTest {

    @LocalServerPort
    private int port;

    TestRestTemplate testRestTemplate;
    HttpHeaders headers;

    @Before
    public void setUp() {
        testRestTemplate = new TestRestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void shouldCreateCustomer(){
        CustomerDetails customer = getCustomer();
        HttpEntity<CustomerDetails> entity = new HttpEntity<>(customer, headers);
        ResponseEntity<String> response = testRestTemplate.exchange(createURLWithPort("/create"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
    }

    @Test
    public void shouldGetAccount(){
        ResponseEntity<Account> response = testRestTemplate.exchange(createURLWithPort("/account/1"),
                HttpMethod.GET, null, Account.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(AccountStatusType.ACTIVE,response.getBody().getStatus());
        assertEquals("Saving",response.getBody().getType());
        assertEquals(new BigDecimal("5000"),response.getBody().getOverDrawnLimit());
    }

    @Test
    public void shouldNotGetAccountIfNotExists(){
        ResponseEntity response = testRestTemplate.exchange(createURLWithPort("/account/2"),
                HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Account Not found"));
    }

    @Test
    public void shouldUpdateAccount(){

        Account account = getAccountModel();
        HttpEntity<Account> entity = new HttpEntity<>(account, headers);

        ResponseEntity response = testRestTemplate.exchange(createURLWithPort("/account/update"),
                HttpMethod.PUT, entity, Account.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldNotUpdateAccountIfNotFound(){

        Account account = getAccountModel();
        account.setNumber(2L);
        HttpEntity<Account> entity = new HttpEntity<>(account, headers);

        ResponseEntity response = testRestTemplate.exchange(createURLWithPort("/account/update"),
                HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Account Not found"));
    }

    private Account getAccountModel() {
        Account account = getAccount(getAddress()) ;
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(1000));
        transaction.setDescription("test transction");
        transaction.setType(TransactionType.CREDIT);
        account.getTransactions().add(transaction);
        return account;
    }


    private CustomerDetails getCustomer() {
        CustomerDetails customer = new CustomerDetails();
        customer.setFirstName("Amitabh");
        customer.setLastName("Bachpan");
        customer.setEmail("ami@tabh.com");
        customer.setContactNumber("99999999999");
        customer.setUni("xyz12345");
        Address address = getAddress();
        customer.setAddress(address);
        Account account = getAccount(address);
        customer.getAccounts().add(account);
        return customer;
    }

    private Account getAccount(Address address) {
        Account account = new Account();
        account.setNumber(1L);
        account.setBalance(new BigDecimal(1000.00));
        account.setOpeningDate(new Date());
        account.setBranch(new Branch("12344", "Magarpatta", address));
        account.setOverDrawnLimit(new BigDecimal(5000.00));
        account.setStatus(AccountStatusType.ACTIVE);
        account.setType("Saving");
        return account;
    }

    private Address getAddress() {
        Address address = new Address();
        address.setLine1("1233");
        address.setLine2("Lane 2");
        address.setCity("Mumbai");
        address.setState("MH");
        address.setCountry("India");
        address.setPincode("411013");
        return address;
    }


}