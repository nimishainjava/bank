package com.springernature.oasis.service;

import com.springernature.oasis.domain.Customer;
import com.springernature.oasis.model.Account;
import com.springernature.oasis.model.CustomerDetails;
import com.springernature.oasis.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void create(CustomerDetails customerDetail) {
        ModelMapper mapper = new ModelMapper();
        Customer customer = mapper.map(customerDetail, Customer.class);
        customer.setId(1L);
        customerRepository.save(customer);
    }

    public Account getAccount(Long accountId) throws Exception {
       Customer customer = customerRepository.findcustomerByAccountNumber(accountId);
       if(customer != null){
           com.springernature.oasis.domain.Account account1 = customer.getAccounts().stream().filter(account -> account.getNumber().equals(accountId)).findFirst().get();
           ModelMapper modelMapper = new ModelMapper();
           return modelMapper.map(account1,Account.class);
       }else {
           throw new HttpClientErrorException(HttpStatus.NOT_FOUND,"No Account found");
       }
    }
}
