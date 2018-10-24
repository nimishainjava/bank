package com.springernature.oasis.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.dom4j.Branch;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = {"number"})
public  class Account {

    private Long number;
    private String type;
    private Branch branch;
    private String balance;
    private Long overDrawnLimit;
    private Date openingDate;
    private String status;
    private Set<Transaction> transactions;

}