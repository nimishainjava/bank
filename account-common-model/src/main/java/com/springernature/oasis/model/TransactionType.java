package com.springernature.oasis.model;

public enum TransactionType {
    DEBIT("Dr"),
    CREDIT("Cr"),
    TRANSFER("Tr");

    private String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
