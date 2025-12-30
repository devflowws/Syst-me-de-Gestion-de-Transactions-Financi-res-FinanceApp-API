package com.financeapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;
    
    @Enumerated(EnumType.STRING)
    private AccountType type = AccountType.CHECKING;
    
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;
    
    private String currency = "EUR";
    
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;
    
    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "overdraft_limit", precision = 19, scale = 4)
    private BigDecimal overdraftLimit = BigDecimal.ZERO;
    
    @Column(name = "daily_limit", precision = 19, scale = 4)
    private BigDecimal dailyLimit = new BigDecimal("1000.00");
    
    // GETTERS
    public String getId() { return id; }
    public String getAccountNumber() { return accountNumber; }
    public AccountType getType() { return type; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public AccountStatus getStatus() { return status; }
    public LocalDate getCreatedDate() { return createdDate; }
    public User getUser() { return user; }
    public BigDecimal getOverdraftLimit() { return overdraftLimit; }
    public BigDecimal getDailyLimit() { return dailyLimit; }
    
    // SETTERS
    public void setId(String id) { this.id = id; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setType(AccountType type) { this.type = type; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setStatus(AccountStatus status) { this.status = status; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
    public void setUser(User user) { this.user = user; }
    public void setOverdraftLimit(BigDecimal overdraftLimit) { this.overdraftLimit = overdraftLimit; }
    public void setDailyLimit(BigDecimal dailyLimit) { this.dailyLimit = dailyLimit; }
    
    @PrePersist
    public void generateAccountNumber() {
        if (accountNumber == null) {
            accountNumber = "ACC" + System.currentTimeMillis() + (int)(Math.random() * 1000);
        }
    }
    
    public enum AccountType {
        CHECKING, SAVINGS, BUSINESS
    }
    
    public enum AccountStatus {
        ACTIVE, FROZEN, CLOSED
    }
}