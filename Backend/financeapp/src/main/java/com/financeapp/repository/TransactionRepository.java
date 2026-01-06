package com.financeapp.repository;

import com.financeapp.entity.Account;
import com.financeapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    
    // Trouver toutes les transactions d'un utilisateur (en tant qu'expéditeur ou destinataire)
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.user.id = :userId OR t.toAccount.user.id = :userId ORDER BY t.timestamp DESC")
    List<Transaction> findByUserId(@Param("userId") String userId);
    
    // Trouver les transactions d'un compte spécifique (en tant qu'expéditeur)
    List<Transaction> findByFromAccount(Account account);
    
    // Trouver les transactions d'un compte spécifique (en tant que destinataire)
    List<Transaction> findByToAccount(Account account);
    
    // Trouver les transactions d'un compte (dans les deux sens)
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount = :account OR t.toAccount = :account ORDER BY t.timestamp DESC")
    List<Transaction> findByAccount(@Param("account") Account account);
    
    // Trouver les transactions d'un compte après une certaine date (en tant qu'expéditeur)
    List<Transaction> findByFromAccountAndTimestampAfter(Account account, LocalDateTime timestamp);
    
    // Trouver les transactions d'un compte après une certaine date (en tant que destinataire)
    List<Transaction> findByToAccountAndTimestampAfter(Account account, LocalDateTime timestamp);
    
    // Calculer le total des transactions aujourd'hui pour un compte (débits uniquement)
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.fromAccount = :account " +
           "AND DATE(t.timestamp) = :date " +
           "AND t.status = 'COMPLETED'")
    BigDecimal sumAmountToday(@Param("account") Account account, @Param("date") LocalDate date);
    
    // Calculer le total des transactions aujourd'hui pour un compte (méthode alternative avec LocalDateTime)
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t " +
           "WHERE t.fromAccount = :account " +
           "AND t.timestamp >= :startOfDay " +
           "AND t.timestamp < :endOfDay " +
           "AND t.status = 'COMPLETED'")
    BigDecimal getDailyTotal(@Param("account") Account account, 
                            @Param("startOfDay") LocalDateTime startOfDay,
                            @Param("endOfDay") LocalDateTime endOfDay);
    
    // Trouver les transactions par type
    List<Transaction> findByType(Transaction.TransactionType type);
    
    // Trouver les transactions par statut
    List<Transaction> findByStatus(Transaction.TransactionStatus status);
    
    // Trouver les transactions dans une période donnée pour un utilisateur
    @Query("SELECT t FROM Transaction t " +
           "WHERE (t.fromAccount.user.id = :userId OR t.toAccount.user.id = :userId) " +
           "AND t.timestamp BETWEEN :startDate AND :endDate " +
           "ORDER BY t.timestamp DESC")
    List<Transaction> findByUserIdAndDateRange(@Param("userId") String userId,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);
    
    // Trouver les transactions pour un compte dans une période
    @Query("SELECT t FROM Transaction t " +
           "WHERE (t.fromAccount = :account OR t.toAccount = :account) " +
           "AND t.timestamp BETWEEN :startDate AND :endDate " +
           "ORDER BY t.timestamp DESC")
    List<Transaction> findByAccountAndDateRange(@Param("account") Account account,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);
    
    // Vérifier si une transaction existe pour une référence
    // Optional<Transaction> findByTransactionReference(String transactionReference);
    
    // Compter le nombre de transactions d'un utilisateur
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.fromAccount.user.id = :userId OR t.toAccount.user.id = :userId")
    long countByUserId(@Param("userId") String userId);
    
    // Trouver les dernières N transactions d'un utilisateur
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.user.id = :userId OR t.toAccount.user.id = :userId ORDER BY t.timestamp DESC LIMIT :limit")
    List<Transaction> findRecentByUserId(@Param("userId") String userId, @Param("limit") int limit);
    
    // Trouver les transactions par montant minimum
    @Query("SELECT t FROM Transaction t WHERE t.amount >= :minAmount ORDER BY t.timestamp DESC")
    List<Transaction> findByAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);
    
    // Trouver les transactions par montant maximum
    @Query("SELECT t FROM Transaction t WHERE t.amount <= :maxAmount ORDER BY t.timestamp DESC")
    List<Transaction> findByAmountLessThanEqual(@Param("maxAmount") BigDecimal maxAmount);
    
    // Trouver les transactions par plage de montant
    @Query("SELECT t FROM Transaction t WHERE t.amount BETWEEN :minAmount AND :maxAmount ORDER BY t.timestamp DESC")
    List<Transaction> findByAmountBetween(@Param("minAmount") BigDecimal minAmount, 
                                          @Param("maxAmount") BigDecimal maxAmount);
    
    // Trouver les transactions par description (recherche partielle)
    @Query("SELECT t FROM Transaction t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY t.timestamp DESC")
    List<Transaction> findByDescriptionContainingIgnoreCase(@Param("keyword") String keyword);
}