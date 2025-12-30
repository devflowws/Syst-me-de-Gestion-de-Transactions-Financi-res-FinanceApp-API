package com.financeapp.service.interfaces;

import com.financeapp.dto.TransactionRequest;
import com.financeapp.entity.Transaction;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(TransactionRequest request);
    List<Transaction> getUserTransactions();
    Transaction getTransactionById(String transactionId);
}