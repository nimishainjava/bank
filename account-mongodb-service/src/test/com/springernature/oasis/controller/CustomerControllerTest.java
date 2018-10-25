package com.springernature.oasis.controller;

import com.springernature.oasis.Application;
import com.springernature.oasis.domain.Customer;
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

import static org.junit.Assert.*;
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
    public void shouldNotGetAccount(){
        ResponseEntity response = testRestTemplate.exchange(createURLWithPort("/account/2"),
                HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    private CustomerDetails getCustomer() {
        CustomerDetails customer = new CustomerDetails();
        customer.setFirstName("Amitabh");
        customer.setLastName("Bachpan");
        customer.setEmail("ami@tabh.com");
        customer.setContactNumber("99999999999");
        customer.setUni("xyz12345");
        Address address = new Address();
        address.setLine1("1233");
        address.setLine2("Lane 2");
        address.setCity("Mumbai");
        address.setState("MH");
        address.setCountry("India");
        address.setPincode("411013");
        customer.setAddress(address);
        Account account = new Account();
        account.setNumber(1L);
        account.setBalance(new BigDecimal(1000.00));
        account.setOpeningDate(new Date());
        account.setBranch(new Branch("12344", "Magarpatta", address));
        account.setOverDrawnLimit(new BigDecimal(5000.00));
        account.setStatus(AccountStatusType.ACTIVE);
        account.setType("Saving");
        customer.getAccounts().add(account);
        return customer;
    }


}