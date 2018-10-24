package com.springernature.oasis.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Set;

@Document
@Getter
@Setter
public class Customer {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String uni;

    private Address address;
    private Set<Account> accounts;
}