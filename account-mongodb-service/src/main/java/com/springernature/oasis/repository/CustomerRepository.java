package com.springernature.oasis.repository;

import com.springernature.oasis.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {

    @Query(value = "{ 'accounts.number' : ?0 }")
    Customer findcustomerByAccountNumber(Long account);


}
