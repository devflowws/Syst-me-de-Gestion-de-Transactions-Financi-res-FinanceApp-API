package com.financeapp.repository;

import com.financeapp.entity.Account;
import com.financeapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.user.id = :userId OR t.toAccount.user.id = :userId ORDER BY t.timestamp DESC")
    List<Transaction> findByUserId(@Param("userId") String userId);
    
    List<Transaction> findByFromAccountAndTimestampAfter(Account account, LocalDateTime timestamp);
    
    List<Transaction> findByToAccountAndTimestampAfter(Account account, LocalDateTime timestamp);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.fromAccount = :account AND t.timestamp >= :startDate AND t.status = 'COMPLETED'")
    BigDecimal getDailyTotal(@Param("account") Account account, @Param("startDate") LocalDateTime startDate);
}