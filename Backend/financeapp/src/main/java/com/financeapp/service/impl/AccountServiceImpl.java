package com.financeapp.service.impl;

import com.financeapp.entity.Account;
import com.financeapp.entity.User;
import com.financeapp.repository.AccountRepository;
import com.financeapp.service.interfaces.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    
    private final AccountRepository accountRepository;
    
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public List<Account> getUserAccounts(User user) {
        return accountRepository.findByUser(user);
    }
    
    @Override
    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé"));
    }
    
    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte non trouvé"));
    }
    
    @Override
    @Transactional
    public Account createAccount(User user, Account.AccountType type) {
        Account account = new Account();
        account.setUser(user);
        account.setType(type);
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }
    
    @Override
    @Transactional
    public Account updateBalance(String accountId, BigDecimal amount, boolean isCredit) {
        Account account = getAccountById(accountId);
        
        BigDecimal newBalance = isCredit ? 
                account.getBalance().add(amount) : 
                account.getBalance().subtract(amount);
        
        if (newBalance.compareTo(account.getOverdraftLimit().negate()) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fonds insuffisants");
        }
        
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }
}