package com.example.banking_transaction_servicemanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.banking_transaction_servicemanagement.dto.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface AccountRepo extends JpaRepository<Account, Integer> {
    
    // Custom method to find by account holder's name
    Account findByAccountHolderName(String accountHolderName);
    
    // Custom method to find by email
    Account findByEmail(String email);
    
    // Custom method to find accounts by account type
    List<Account> findByAccountType(String accountType);
    
    // Custom method to find active accounts
    List<Account> findByActive(Boolean active);
    
    // Custom method to find accounts with balance greater than a specified value
    @Query("SELECT a FROM Account a WHERE a.balance > ?1")
    List<Account> findAccountsWithBalanceGreaterThan(Double balance);

  @Modifying
@Query("UPDATE Account a SET a.isActive = false WHERE a.accId = :accountId")
void softDeleteById(@Param("accountId") int accountId);


}


