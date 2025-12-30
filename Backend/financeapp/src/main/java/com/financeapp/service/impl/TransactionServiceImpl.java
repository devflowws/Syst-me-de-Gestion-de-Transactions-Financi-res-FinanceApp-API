package com.financeapp.service.impl;

import com.financeapp.dto.TransactionRequest;
import com.financeapp.entity.Account;
import com.financeapp.entity.Transaction;
import com.financeapp.entity.User;
import com.financeapp.repository.TransactionRepository;
import com.financeapp.service.interfaces.AccountService;
import com.financeapp.service.interfaces.TransactionService;
import com.financeapp.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final UserService userService;
    
    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountService accountService,
            UserService userService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.userService = userService;
    }
    
    @Override
    @Transactional
    public Transaction createTransaction(TransactionRequest request) {
        User currentUser = userService.getCurrentUser();
        Account fromAccount = accountService.getAccountById(request.getFromAccountId());
        Account toAccount = accountService.getAccountById(request.getToAccountId());
        
        // Vérifier la propriété
        if (!fromAccount.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas propriétaire du compte source");
        }
        
        // Vérifier les fonds
        if (fromAccount.getBalance().subtract(request.getAmount())
                .compareTo(fromAccount.getOverdraftLimit().negate()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fonds insuffisants");
        }
        
        // Effectuer la transaction
        Account updatedFrom = accountService.updateBalance(fromAccount.getId(), request.getAmount(), false);
        Account updatedTo = accountService.updateBalance(toAccount.getId(), request.getAmount(), true);
        
        // Enregistrer
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDescription(request.getDescription());
        transaction.setCategory(request.getCategory());
        transaction.setFromAccount(updatedFrom);
        transaction.setToAccount(updatedTo);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setBalanceAfterTransaction(updatedFrom.getBalance());
        
        return transactionRepository.save(transaction);
    }
    
    @Override
    public List<Transaction> getUserTransactions() {
        User user = userService.getCurrentUser();
        return transactionRepository.findByUserId(user.getId());
    }
    
    @Override
    public Transaction getTransactionById(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction non trouvée"));
    }
}