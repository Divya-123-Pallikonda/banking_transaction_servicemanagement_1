package com.example.banking_transaction_servicemanagement.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.banking_transaction_servicemanagement.dto.Account;
import com.example.banking_transaction_servicemanagement.exception.AccountNotFoundException;
import com.example.banking_transaction_servicemanagement.repo.AccountRepo;

@Repository
public class AccountDao {

    @Autowired
    AccountRepo accountRepo;

    public Account saveAccount(Account account) {
        return accountRepo.save(account);
    }

    public Account fetchAccountById(int accId) {
        return accountRepo.findById(accId)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accId + " not found in DB"));
    }

    public Account updateAccountById(int oldAccId, Account newAccount) {
        newAccount.setAccountId(oldAccId);
        return accountRepo.save(newAccount);
    }

    public Account deleteAccountById(int accId) {
        Account account = fetchAccountById(accId);
        accountRepo.delete(account);  
        return account;
    }

    public List<Account> fetchAllAccounts() {
        return accountRepo.findAll();
    }
    
    public Account softDeleteAccount(int accId) {
        Account account = fetchAccountById(accId);
        account.setActive(false);
        return accountRepo.save(account);
    }
   
}
