package com.springernature.oasis.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(of = {"number"})
public class Transaction {

    private Long number;
    private String type;
    private Date date;
    private BigDecimal amount;
    private String description;

}
