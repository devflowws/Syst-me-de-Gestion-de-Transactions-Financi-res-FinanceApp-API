package com.financeapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(unique = true, nullable = false)
    private String reference;
    
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.PENDING;
    
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;
    
    @CreationTimestamp
    private LocalDateTime timestamp;
    
    @Column(name = "balance_after", precision = 19, scale = 4)
    private BigDecimal balanceAfterTransaction;
    
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;
    
    // Constructeur par défaut
    public Transaction() {
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getReference() {
        return reference;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public TransactionStatus getStatus() {
        return status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Account getFromAccount() {
        return fromAccount;
    }
    
    public Account getToAccount() {
        return toAccount;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }
    
    public TransactionCategory getCategory() {
        return category;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setReference(String reference) {
        this.reference = reference;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }
    
    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
    
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }
    
    @PrePersist
    public void generateReference() {
        if (reference == null) {
            reference = "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
        }
    }
    
    public enum TransactionType {
        TRANSFER, DEPOSIT, WITHDRAWAL, PAYMENT, TRANSFER_EXTERNAL
    }
    
    public enum TransactionStatus {
        PENDING, COMPLETED, FAILED, CANCELLED
    }
    
    public enum TransactionCategory {
        FOOD, TRANSPORT, SHOPPING, ENTERTAINMENT, UTILITIES, SALARY, OTHER
    }
    
    // Méthodes utilitaires
    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", reference='" + reference + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", category=" + category +
                '}';
    }
}