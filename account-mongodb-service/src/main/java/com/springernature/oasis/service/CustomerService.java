package com.springernature.oasis.service;

import com.springernature.oasis.domain.Customer;
import com.springernature.oasis.domain.Transaction;
import com.springernature.oasis.exception.MongoDbServiceException;
import com.springernature.oasis.model.Account;
import com.springernature.oasis.model.CustomerDetails;
import com.springernature.oasis.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

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
           throw new MongoDbServiceException("Account Not found",HttpStatus.NOT_FOUND);
       }
    }

    public Account updateAccount(Account account) throws Exception {
        if(account.getNumber() != null){
            Customer customer = customerRepository.findcustomerByAccountNumber(account.getNumber());
            if(customer != null){
                populateAccountForCustomer(account, customer);
                customerRepository.save(customer);
                return account;
            }else {
                throw new MongoDbServiceException("Account Not found",HttpStatus.NOT_FOUND);
            }
        }else {
            throw new MongoDbServiceException("Account number is not present",HttpStatus.BAD_REQUEST);
        }
    }

    private void populateAccountForCustomer(Account account, Customer customer) {
        ModelMapper modelMapper = new ModelMapper();
        com.springernature.oasis.domain.Account accountDomain = modelMapper.map(account, com.springernature.oasis.domain.Account.class);
        updateTranscationDetails(accountDomain);
        customer.getAccounts().remove(accountDomain);
        customer.getAccounts().add(accountDomain);
    }

    private void updateTranscationDetails(com.springernature.oasis.domain.Account accountDomain) {
        Long transactionNumber = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        Transaction transaction = accountDomain.getTransactions().stream().filter(tran -> tran.getNumber() == null).findAny().get();
        transaction.setNumber(transactionNumber);
        transaction.setDate(new Date());
    }
}
