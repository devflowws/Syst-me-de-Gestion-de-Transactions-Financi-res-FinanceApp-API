package com.financeapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;
    
    @Column(name = "account_name")
    private String accountName;
    
    @Enumerated(EnumType.STRING)
    private AccountType type = AccountType.CHECKING;
    
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;
    
    private String currency = "EUR";
    
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "overdraft_limit", precision = 19, scale = 4)
    private BigDecimal overdraftLimit = BigDecimal.ZERO;
    
    @Column(name = "daily_limit", precision = 19, scale = 4)
    private BigDecimal dailyLimit = new BigDecimal("1000.00");
    
    // Constructeurs
    public Account() {
    }
    
    public Account(String accountName, AccountType type, User user) {
        this.accountName = accountName;
        this.type = type;
        this.user = user;
        generateAccountNumber();
    }
    
    // GETTERS
    public String getId() { 
        return id; 
    }
    
    public String getAccountNumber() { 
        return accountNumber; 
    }
    
    public String getAccountName() { 
        return accountName; 
    }
    
    public AccountType getType() { 
        return type; 
    }
    
    public BigDecimal getBalance() { 
        return balance; 
    }
    
    public String getCurrency() { 
        return currency; 
    }
    
    public AccountStatus getStatus() { 
        return status; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }
    
    public User getUser() { 
        return user; 
    }
    
    public BigDecimal getOverdraftLimit() { 
        return overdraftLimit; 
    }
    
    public BigDecimal getDailyLimit() { 
        return dailyLimit; 
    }
    
    // SETTERS
    public void setId(String id) { 
        this.id = id; 
    }
    
    public void setAccountNumber(String accountNumber) { 
        this.accountNumber = accountNumber; 
    }
    
    public void setAccountName(String accountName) { 
        this.accountName = accountName; 
    }
    
    public void setType(AccountType type) { 
        this.type = type; 
    }
    
    public void setBalance(BigDecimal balance) { 
        this.balance = balance; 
    }
    
    public void setCurrency(String currency) { 
        this.currency = currency; 
    }
    
    public void setStatus(AccountStatus status) { 
        this.status = status; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) { 
        this.updatedAt = updatedAt; 
    }
    
    public void setUser(User user) { 
        this.user = user; 
    }
    
    public void setOverdraftLimit(BigDecimal overdraftLimit) { 
        this.overdraftLimit = overdraftLimit; 
    }
    
    public void setDailyLimit(BigDecimal dailyLimit) { 
        this.dailyLimit = dailyLimit; 
    }
    
    // Méthode pour générer le numéro de compte
    @PrePersist
    public void generateAccountNumber() {
        if (accountNumber == null) {
            long timestamp = System.currentTimeMillis() % 1000000;
            int random = (int) (Math.random() * 1000);
            this.accountNumber = "ACC" + timestamp + String.format("%03d", random);
        }
    }
    
    // Méthodes utilitaires
    public boolean canWithdraw(BigDecimal amount) {
        BigDecimal availableBalance = balance.add(overdraftLimit);
        return amount.compareTo(availableBalance) <= 0 && 
               amount.compareTo(dailyLimit) <= 0;
    }
    
    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
    
    public void withdraw(BigDecimal amount) {
        if (canWithdraw(amount)) {
            this.balance = this.balance.subtract(amount);
        } else {
            throw new IllegalArgumentException("Insufficient funds or exceeds daily limit");
        }
    }
    
    public enum AccountType {
        CHECKING("Compte courant"),
        SAVINGS("Épargne"),
        BUSINESS("Compte professionnel");
        
        private final String description;
        
        AccountType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    public enum AccountStatus {
        ACTIVE("Actif"),
        FROZEN("Gelé"),
        CLOSED("Fermé");
        
        private final String description;
        
        AccountStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
}