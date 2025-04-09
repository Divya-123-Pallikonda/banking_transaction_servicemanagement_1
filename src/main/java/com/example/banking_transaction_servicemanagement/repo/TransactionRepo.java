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

    // ✅ MAIN: Filtered & paginated by account
    @Query("SELECT t FROM Transaction t WHERE t.account.accId = :accountId " +
           "AND (:type IS NULL OR t.type = :type) " +
           "AND (:startDate IS NULL OR t.timestamp >= :startDate) " +
           "AND (:endDate IS NULL OR t.timestamp <= :endDate)")
    Page<Transaction> findByFilters(
        @Param("accountId") int accountId,
        @Param("type") String type,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        Pageable pageable);

    // Optional: for case-insensitive search
    Page<Transaction> findByAccountAccIdAndTypeContainingIgnoreCase(
        int accId, String type, Pageable pageable);
}
