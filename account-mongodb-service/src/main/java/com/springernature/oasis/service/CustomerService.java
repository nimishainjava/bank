package com.springernature.oasis.service;

import com.springernature.oasis.domain.Customer;
import com.springernature.oasis.model.CustomerDetails;
import com.springernature.oasis.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
