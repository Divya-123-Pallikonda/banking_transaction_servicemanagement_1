package com.example.banking_transaction_servicemanagement.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.banking_transaction_servicemanagement.dao.AccountDao;
import com.example.banking_transaction_servicemanagement.dao.TransactionDao;
import com.example.banking_transaction_servicemanagement.dto.Account;
import com.example.banking_transaction_servicemanagement.dto.Transaction;
import com.example.banking_transaction_servicemanagement.dto.TransactionRequest;
import com.example.banking_transaction_servicemanagement.dto.TransactionResponse;
import com.example.banking_transaction_servicemanagement.exception.AccountNotFoundException;
import com.example.banking_transaction_servicemanagement.exception.TransactionNotFoundException;
import com.example.banking_transaction_servicemanagement.repo.AccountRepo;
import com.example.banking_transaction_servicemanagement.repo.TransactionRepo;

import jakarta.transaction.Transactional;


@Service
public class TransactionService  {

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
	 
	 public Transaction updateTransactionByid(int oldTid,Transaction newTransaction) {
			return transactionDao.updateTransactionByid(oldTid,newTransaction);
}
	 
	 public Transaction fetchTransactionById(int Tid) {
		    return transactionDao.fetchTransactionById(Tid);
		}
	    
		public Transaction deleteTransactionById(int Tid) {
			return transactionDao.deleteTransactionById(Tid);
		}
		
		public List<Transaction> fetchAllTransactions(){
			return transactionDao.fetchAllTransaction();
		}
		@Transactional
		public TransactionResponse makeTransaction(int accId, TransactionRequest request) {
		    Account account = accountDao.fetchAccountById(accId);
		    if (account == null || !account.getActive()) 
		        throw new AccountNotFoundException("Invalid or inactive account with id: " + accId);

		    Transaction transaction = new Transaction();
		    transaction.setAmount(request.getAmount());
		    transaction.setType(request.getType());
		    transaction.setTimestamp(request.getTimestamp() != null ? request.getTimestamp() : LocalDateTime.now());

		    if ("debit".equalsIgnoreCase(request.getType())) {
		        if (account.getBalance() < request.getAmount()) throw new RuntimeException("Insufficient balance");
		        account.setBalance(account.getBalance() - request.getAmount());
		    } else if ("credit".equalsIgnoreCase(request.getType())) {
		        account.setBalance(account.getBalance() + request.getAmount());
		    } else {
		        throw new RuntimeException("Invalid transaction type");
		    }

		    transaction.setStatus("success");

		    accountDao.saveAccount(account);
		    transaction = transactionDao.saveTransaction(transaction);

		    TransactionResponse response = new TransactionResponse();
		    response.setTrid(transaction.getTid());
		    response.setAmount(transaction.getAmount());
		    response.setType(transaction.getType());
		    response.setTimestamp(transaction.getTimestamp());
		    response.setStatus(transaction.getStatus());
		    return response;

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
}

