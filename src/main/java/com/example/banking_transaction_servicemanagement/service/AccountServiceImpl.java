package com.example.banking_transaction_servicemanagement.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking_transaction_servicemanagement.dao.AccountDao;
import com.example.banking_transaction_servicemanagement.dto.Account;
import com.example.banking_transaction_servicemanagement.request.CreateAccountRequest;
import java.math.BigDecimal;

import com.example.banking_transaction_servicemanagement.exception.ResourceNotFoundException;
import com.example.banking_transaction_servicemanagement.repo.AccountRepo;



@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	  AccountDao accountDao;
	@Autowired 
	AccountRepo accountRepo;

	public Account saveAccount(Account account) {
        return accountDao.saveAccount(account);
    }
	
	public Account updateAccountById(int oldAccId,Account newAccount) {
		return accountDao.updateAccountById( oldAccId,newAccount);
	}
	
	public Account fetchAccountById(int accId) {
	    return accountDao.fetchAccountById(accId);
	}
    
	public Account deleteAccountById(int accId) {
		return accountDao.deleteAccountById(accId);
		
	}
	
	public List<Account> fetchAllAccounts(){
		return accountDao.fetchAllAccounts();
	}
	public Account softDeleteAccount(int accId) {
	    return accountDao.softDeleteAccount(accId);
	}
	  
	    public Account createAccount(CreateAccountRequest request) {
	        Account account = new Account();
	        account.setAccountHolderName(request.getAccountHolderName());
	        account.setEmail(request.getEmail());
	        account.setPhoneNumber(request.getPhoneNumber());
	        account.setAccountType(request.getAccountType());
	        account.setBalance(request.getInitialBalance());
	        account.setActive(true);
	        
	        return accountDao.saveAccount(account); 
	    }
	public BigDecimal getAccountBalance(int id) {
    Account account = accountRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    return account.getBalance();
}

}



	


