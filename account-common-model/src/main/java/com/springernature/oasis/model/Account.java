package com.springernature.oasis.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Account {

    private Long number;
    private String type;
    private Branch branch;
    private BigDecimal balance;
    private BigDecimal overDrawnLimit;
    private Date openingDate;
    private AccountStatusType status;
    private Set<Transaction> transactions;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getOverDrawnLimit() {
        return overDrawnLimit;
    }

    public void setOverDrawnLimit(BigDecimal overDrawnLimit) {
        this.overDrawnLimit = overDrawnLimit;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public AccountStatusType getStatus() {
        return status;
    }

    public void setStatus(AccountStatusType status) {
        this.status = status;
    }

    public Set<Transaction> getTransactions() {
        if(null == transactions)
            transactions=new HashSet<Transaction>();
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}