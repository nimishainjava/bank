package com.springernature.oasis.domain;


import java.util.Date;
import java.util.Set;


public  class Account {

    private Long number;
    private String type;
    private Branch branch;
    private String balance;
    private Long overDrawnLimit;
    private Date openingDate;
    private String status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}