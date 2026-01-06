package com.financeapp.service.impl;

import com.financeapp.dto.TransactionRequest;
import com.financeapp.entity.Account;
import com.financeapp.entity.Transaction;
import com.financeapp.entity.User;
import com.financeapp.repository.AccountRepository;
import com.financeapp.repository.TransactionRepository;
import com.financeapp.service.interfaces.AccountService;
import com.financeapp.service.interfaces.TransactionService;
import com.financeapp.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserService userService;
    
    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            UserService userService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.userService = userService;
    }
    
    @Override
    @Transactional
    public Transaction createTransaction(TransactionRequest request) {
        try {
            // Récupérer l'utilisateur courant
            User currentUser = userService.getCurrentUser();
            
            // Récupérer les comptes
            Account fromAccount = accountRepository.findById(request.getFromAccountId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte source non trouvé"));
            
            Account toAccount = accountRepository.findById(request.getToAccountId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte destinataire non trouvé"));
            
            // Vérifier la propriété du compte source
            if (!fromAccount.getUser().getId().equals(currentUser.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas propriétaire du compte source");
            }
            
            // Vérifier que le montant est positif
            if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le montant doit être positif");
            }
            
            // Vérifier les fonds disponibles
            BigDecimal availableBalance = fromAccount.getBalance().add(fromAccount.getOverdraftLimit());
            if (request.getAmount().compareTo(availableBalance) > 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fonds insuffisants");
            }
            
            // Vérifier la limite quotidienne
            BigDecimal todayTransactions = transactionRepository.sumAmountToday(fromAccount, LocalDate.now());
            
            // Mettre à jour les soldes
            fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
            toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
            
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            
            // Créer la transaction
            Transaction transaction = new Transaction();
            transaction.setAmount(request.getAmount());
            transaction.setDescription(request.getDescription());
            transaction.setFromAccount(fromAccount);
            transaction.setToAccount(toAccount);
            transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
            transaction.setTimestamp(LocalDateTime.now());
            transaction.setBalanceAfterTransaction(fromAccount.getBalance());
            
            // Type déterminé automatiquement
            if (fromAccount.getUser().getId().equals(toAccount.getUser().getId())) {
                transaction.setType(Transaction.TransactionType.TRANSFER);
            } else {
                transaction.setType(Transaction.TransactionType.TRANSFER_EXTERNAL);
            }
            
        
            return transactionRepository.save(transaction);
            
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la transaction: " + e.getMessage());
        }
    }
    
    @Override
    public List<Transaction> getUserTransactions() {
        try {
            User user = userService.getCurrentUser();
            return transactionRepository.findByUserId(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des transactions");
        }
    }
    
    @Override
    public Transaction getTransactionById(String transactionId) {
        try {
            Transaction transaction = transactionRepository.findById(transactionId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction non trouvée"));
            
            // Vérifier que l'utilisateur a accès à cette transaction
            User user = userService.getCurrentUser();
            if (!transaction.getFromAccount().getUser().getId().equals(user.getId()) &&
                !transaction.getToAccount().getUser().getId().equals(user.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Accès non autorisé à cette transaction");
            }
            
            return transaction;
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération de la transaction");
        }
    }
}