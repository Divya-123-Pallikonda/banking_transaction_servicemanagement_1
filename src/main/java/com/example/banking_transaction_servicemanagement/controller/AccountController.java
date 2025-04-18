package com.example.banking_transaction_servicemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.banking_transaction_servicemanagement.dto.Account;
import com.example.banking_transaction_servicemanagement.request.CreateAccountRequest;
import com.example.banking_transaction_servicemanagement.service.AccountService;


@RestController
public class AccountController {

    
    @Autowired
    AccountService accountService;

    @PostMapping("/CreateAccountRequest")
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account createdAccount = accountService.createAccount(request);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    
    @PutMapping("/updateAccountById")
    public Account updateAccountById(@RequestParam int oldAccId,@RequestBody Account newAccount) {
		return accountService.updateAccountById( oldAccId,newAccount);
	}
    
    @GetMapping("/fetchAccountById")
    public Account fetchAccountById(@RequestParam int accId) {
	    return accountService.fetchAccountById(accId);
	}
    
    @DeleteMapping("/deleteAccountById")
	public Account deleteAccountById(@RequestParam int accId ) {
		return accountService.deleteAccountById(accId);
		
	}
	@GetMapping("/fetchAllAccounts")
	public List<Account> fetchAllAccounts(){
		return accountService.fetchAllAccounts();
	}
	
	@PatchMapping("/softDeleteAccount")
	public Account softDeleteAccount(@RequestParam int accId) {
	    return accountService.softDeleteAccount(accId);
	}
	@GetMapping("/accounts/{accId}")
public ResponseEntity<Account> fetchAccountById(@PathVariable int accId) {
    Account account = accountService.fetchAccountById(accId);
    return ResponseEntity.ok(account);
}
@GetMapping("/accounts")
public List<Account> getAllAccounts() {
    return accountService.getAllAccounts();
}

@DeleteMapping("/accounts/{accountId}")
public ResponseEntity<String> suspendAccount(@PathVariable int accountId) {
    accountService.suspendAccount(accountId);
    return ResponseEntity.ok("Account suspended successfully.");
}

}

	

}
    

