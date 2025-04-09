package com.example.banking_transaction_servicemanagement.service.impl;

import com.example.banking_transaction_servicemanagement.repo.TransactionRepo;
import com.example.banking_transaction_servicemanagement.dto.Transaction;
import com.example.banking_transaction_servicemanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public Page<Transaction> getTransactions(Long accountId, int page, int size, String type, LocalDateTime startDate, LocalDateTime endDate) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepo.findByFilters(accountId, type, startDate, endDate, pageable);
    }
}
