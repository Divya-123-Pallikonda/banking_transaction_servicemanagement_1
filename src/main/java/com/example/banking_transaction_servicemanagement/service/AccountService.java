package com.example.banking_transaction_servicemanagement.service;

import java.util.List;

import com.example.banking_transaction_servicemanagement.dto.Account;
import com.example.banking_transaction_servicemanagement.request.CreateAccountRequest;


public interface AccountService {
    Account saveAccount(Account account);
    Account updateAccountById(int oldAccId, Account newAccount);
    Account fetchAccountById(int accId);
    Account deleteAccountById(int accId);
    List<Account> fetchAllAccounts();
    Account softDeleteAccount(int accId);
    Account createAccount(CreateAccountRequest request);
}




