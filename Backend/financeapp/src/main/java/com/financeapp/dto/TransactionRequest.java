package com.financeapp.dto;

import com.financeapp.entity.Transaction.TransactionCategory;
import com.financeapp.entity.Transaction.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class TransactionRequest {
    
    @NotBlank(message = "Compte source requis")
    private String fromAccountId;
    
    @NotBlank(message = "Compte destination requis")
    private String toAccountId;
    
    @NotNull(message = "Montant requis")
    @DecimalMin(value = "0.01", message = "Montant doit être supérieur à 0")
    private BigDecimal amount;
    
    private String description;
    
    private TransactionType type = TransactionType.TRANSFER;
    
    private TransactionCategory category = TransactionCategory.OTHER;
    
    // Constructeurs
    public TransactionRequest() {
    }
    
    public TransactionRequest(String fromAccountId, String toAccountId, BigDecimal amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }
    
    // Getters
    public String getFromAccountId() {
        return fromAccountId;
    }
    
    public String getToAccountId() {
        return toAccountId;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public TransactionType getType() {
        return type;
    }
    
    public TransactionCategory getCategory() {
        return category;
    }
    
    // Setters
    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }
    
    public void setToAccountId(String toAccountId) {
        this.toAccountId = toAccountId;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }
    
    public void setCategory(TransactionCategory category) {
        this.category = category;
    }
    
    // toString
    @Override
    public String toString() {
        return "TransactionRequest{" +
                "fromAccountId='" + fromAccountId + '\'' +
                ", toAccountId='" + toAccountId + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", category=" + category +
                '}';
    }
}