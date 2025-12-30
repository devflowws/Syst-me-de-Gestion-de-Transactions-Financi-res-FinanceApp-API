package com.financeapp.controller;

import com.financeapp.entity.Account;
import com.financeapp.service.interfaces.AccountService;
import com.financeapp.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    private final AccountService accountService;
    private final UserService userService;
    
    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<List<Account>> getUserAccounts() {
        return ResponseEntity.ok(accountService.getUserAccounts(userService.getCurrentUser()));
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountId) {
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }
    
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestParam Account.AccountType type) {
        return ResponseEntity.ok(accountService.createAccount(userService.getCurrentUser(), type));
    }
}