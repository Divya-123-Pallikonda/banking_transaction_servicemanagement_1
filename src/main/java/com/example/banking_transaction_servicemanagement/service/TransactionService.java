package com.example.banking_transaction_servicemanagement.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.banking_transaction_servicemanagement.dao.AccountDao;
import com.example.banking_transaction_servicemanagement.dao.TransactionDao;
import com.example.banking_transaction_servicemanagement.dto.*;
import com.example.banking_transaction_servicemanagement.exception.*;
import com.example.banking_transaction_servicemanagement.repo.*;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    @Autowired
    TransactionDao transactionDao;

    @Autowired
    AccountDao accountDao;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    TransactionRepo transactionRepo;

    public Transaction saveTransaction(Transaction transaction) {
        return transactionDao.saveTransaction(transaction);
    }

    public Transaction updateTransactionByid(int oldTid, Transaction newTransaction) {
        return transactionDao.updateTransactionByid(oldTid, newTransaction);
    }

    public Transaction fetchTransactionById(int tid) {
        return transactionDao.fetchTransactionById(tid);
    }

    public Transaction deleteTransactionById(int tid) {
        return transactionDao.deleteTransactionById(tid);
    }

    public List<Transaction> fetchAllTransactions() {
        return transactionDao.fetchAllTransaction();
    }

    @Transactional
    public TransactionResponse makeTransaction(int accId, TransactionRequest request) {
        Account account = accountDao.fetchAccountById(accId);
        if (account == null || !account.getActive()) {
            throw new AccountNotFoundException("Invalid or inactive account with id: " + accId);
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setTimestamp(request.getTimestamp() != null ? request.getTimestamp() : LocalDateTime.now());

        BigDecimal amount = request.getAmount();

        if ("debit".equalsIgnoreCase(request.getType())) {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(amount));
        } else if ("credit".equalsIgnoreCase(request.getType())) {
            account.setBalance(account.getBalance().add(amount));
        } else {
            throw new RuntimeException("Invalid transaction type");
        }

        transaction.setStatus("success");
        transaction.setAccount(account);

        accountDao.saveAccount(account);
        transaction = transactionDao.saveTransaction(transaction);

        return new TransactionResponse(
                transaction.getTid(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getTimestamp(),
                transaction.getStatus()
        );
    }

    public Transaction linkTransactionToAccount(int accId, int tid) {
        Account account = accountRepo.findById(accId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + accId));

        Transaction transaction = transactionRepo.findById(tid)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found with id: " + tid));

        transaction.setAccount(account);
        return transactionRepo.save(transaction);
    }

    public List<Transaction> getFilteredTransactions(String type, LocalDateTime start, LocalDateTime end) {
        return transactionDao.getFilteredTransactions(type, start, end);
    }

    public Page<Transaction> getTransactions(Long accountId, int page, int size, String type, LocalDate startDate, LocalDate endDate) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepo.findFiltered(accountId, type, startDate, endDate, pageable);
    }
    public Page<Transaction> getTransactionHistory(Long accountId, String type, LocalDate startDate, LocalDate endDate, Pageable pageable) {
    return transactionRepo.findByFilters(accountId, type, startDate, endDate, pageable);
}

}
