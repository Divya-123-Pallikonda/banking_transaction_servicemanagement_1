package com.example.banking_transaction_servicemanagement.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Transaction updateTransactionByid(@RequestParam int oldTid, @RequestBody Transaction newTransaction) {
        return transactionService.updateTransactionByid(oldTid, newTransaction);
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
    public List<Transaction> fetchAllTransactions() {
        return transactionService.fetchAllTransactions();
    }

    @PostMapping("/makeTransaction")
    public TransactionResponse makeTransaction(@RequestParam int accId, @RequestBody TransactionRequest request) {
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

    @GetMapping("/accounts/{accountId}/transactions")
    public Page<Transaction> getTransactions(
            @PathVariable Long accountId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @PageableDefault(size = 10) Pageable pageable) {
        return transactionService.getTransactions(accountId, type, fromDate, toDate, pageable);
    }
   @GetMapping("/accounts/{accountId}/transactions")
public ResponseEntity<Page<Transaction>> getTransactionHistory(
        @PathVariable Long accountId,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @PageableDefault(size = 10) Pageable pageable) {
    
    Page<Transaction> transactions = transactionService.getTransactionHistory(accountId, type, startDate, endDate, pageable);
    return ResponseEntity.ok(transactions);
}

}
