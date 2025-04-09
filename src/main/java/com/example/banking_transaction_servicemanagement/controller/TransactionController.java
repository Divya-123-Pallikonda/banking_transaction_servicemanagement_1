package com.example.banking_transaction_servicemanagement.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.banking_transaction_servicemanagement.dto.Transaction;
import com.example.banking_transaction_servicemanagement.dto.TransactionRequest;
import com.example.banking_transaction_servicemanagement.dto.TransactionResponse;
import com.example.banking_transaction_servicemanagement.service.TransactionService;


@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/saveTransaction")
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }
    @PutMapping("/updateTransactionByid")
    public Transaction updateTransactionByid(@RequestParam int oldTid,@RequestBody Transaction newTransaction) {
		return transactionService.updateTransactionByid(oldTid,newTransaction);
    
}
    @GetMapping("/fetchTransactionById")
    public Transaction fetchTransactionById(@RequestParam int Tid) {
	    return transactionService.fetchTransactionById(Tid);
	}
    
    @DeleteMapping("/deleteTransactionById")
	public Transaction deleteTransactionById(@RequestParam int Tid) {
		return transactionService.deleteTransactionById(Tid);
	}
	@GetMapping("/fetchAllTransactions")
	public List<Transaction> fetchAllTransactions(){
		return transactionService.fetchAllTransactions();
	}
	
	@PostMapping("/makeTransaction")
	public TransactionResponse makeTransaction(
	        @RequestParam int accId,              
	        @RequestBody TransactionRequest request) {  
	    return transactionService.makeTransaction(accId, request);  
	}
	

	@PatchMapping("/linkTransactionToAccount")
	public Transaction linkTransactionToAccount(@RequestParam int accId, @RequestParam int tid) {
	    return transactionService.linkTransactionToAccount(accId, tid);
	}
	 @GetMapping("/getFilteredTransactions")
	    public List<Transaction> getFilteredTransactions(
	            @RequestParam(required = false) String type,
	            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
	            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

	        return transactionService.getFilteredTransactions(type, start, end);
	    }
@GetMapping("/accounts/{id}/transactions")
public ResponseEntity<Page<Transaction>> getTransactions(
        @PathVariable Long id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

    Page<Transaction> transactions = transactionService.getTransactions(id, page, size, type, startDate, endDate);
    return ResponseEntity.ok(transactions);
}

}
