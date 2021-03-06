package com.springernature.oasis.domain;


import com.springernature.oasis.model.AccountStatusType;

import java.util.Date;
import java.util.Set;


public  class Account {

    private Long number;
    private String type;
    private Branch branch;
    private String balance;
    private Long overDrawnLimit;
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Long getOverDrawnLimit() {
        return overDrawnLimit;
    }

    public void setOverDrawnLimit(Long overDrawnLimit) {
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
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return number.equals(account.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}