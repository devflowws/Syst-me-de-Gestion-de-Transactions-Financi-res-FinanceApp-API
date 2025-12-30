package com.financeapp.service.interfaces;  

import com.financeapp.entity.Account;
import com.financeapp.entity.User;
import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<Account> getUserAccounts(User user);
    Account getAccountById(String accountId);
    Account getAccountByNumber(String accountNumber);
    Account createAccount(User user, Account.AccountType type);
    Account updateBalance(String accountId, BigDecimal amount, boolean isCredit);
}