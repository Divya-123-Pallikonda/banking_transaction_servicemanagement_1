package com.example.banking_transaction_servicemanagement.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.banking_transaction_servicemanagement.dto.Account;
import com.example.banking_transaction_servicemanagement.dto.Transaction;
import com.example.banking_transaction_servicemanagement.exception.AccountNotFoundException;
import com.example.banking_transaction_servicemanagement.exception.TransactionNotFoundException;
import com.example.banking_transaction_servicemanagement.repo.AccountRepo;
import com.example.banking_transaction_servicemanagement.repo.TransactionRepo;

@Repository
public class TransactionDao {

	@Autowired
	 TransactionRepo transactionRepo;
	@Autowired
	AccountRepo accountRepo;

	    public Transaction saveTransaction(Transaction transaction) {
	        return transactionRepo.save(transaction);
        }
	    public Transaction updateTransactionByid(int oldTid,Transaction newTransaction) {
			newTransaction.setTid(oldTid);
			return transactionRepo.save(newTransaction);
		}
         
	    public Transaction fetchTransactionById(int Tid) {
	    	Optional<Transaction> transaction = transactionRepo.findById(Tid);
	    	if (transaction.isPresent()) {
	    		return transaction.get();
	    	} else {
	    		return null;
	    	}
	    }

	    
		public Transaction deleteTransactionById(int Tid) {
			Transaction transaction= fetchTransactionById(Tid);
			transactionRepo.delete(transaction);
			return transaction;
		}
		
		public List<Transaction> fetchAllTransaction(){
			return transactionRepo.findAll();
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
		        return transactionRepo.findFiltered(type, start, end);
		    }
		}


