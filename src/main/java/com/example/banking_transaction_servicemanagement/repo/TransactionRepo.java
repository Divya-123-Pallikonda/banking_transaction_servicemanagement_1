package com.example.banking_transaction_servicemanagement.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.banking_transaction_servicemanagement.dto.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
	 List<Transaction> findByType(String type);

	   
	    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);

	   
	    @Query("SELECT t FROM Transaction t WHERE " +
	           "(:type IS NULL OR t.type = :type) AND " +
	           "(:start IS NULL OR t.timestamp >= :start) AND " +
	           "(:end IS NULL OR t.timestamp <= :end)")
	    List<Transaction> findFiltered(
	            @Param("type") String type,
	            @Param("start") LocalDateTime start,
	            @Param("end") LocalDateTime end);
	   
}@Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId " +
       "AND (:type IS NULL OR t.type = :type) " +
       "AND (:startDate IS NULL OR t.timestamp >= :startDate) " +
       "AND (:endDate IS NULL OR t.timestamp <= :endDate)")
Page<Transaction> findByFilters(@Param("accountId") Long accountId,
                                @Param("type") String type,
                                @Param("startDate") LocalDateTime startDate,
                                @Param("endDate") LocalDateTime endDate,
                                Pageable pageable);

