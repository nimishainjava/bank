package com.springernature.oasis.constants;

public class Messages {

    // Success Messages
    public static final String DEPOSIT_SUCCESS_MSG = "Amount INR {*} has been credited successfully.";
    public static final String WITHDRAW_SUCCESS_MSG = "Amount INR {*} has been debited successfully.";
    public static final String ACCOUNT_TRANSACTIONS_UPDATED_SUCCESSFULLY_MSG = "Transaction has been logged successfully";

    // Error Messages
    public static final String INSUFFICIENT_BALANCE_MSG = "The account number entered does not have sufficient funds.";
    public static final String ACCOUNT_INACTIVE_MSG = "Account is not active";
    public static final String ACCOUNT_INVALID_MSG = "Account Number is invalid";
    public static final String INTERNAL_SERVER_ERROR_MSG = "The server seems to be broken. Please try again later.";
}
