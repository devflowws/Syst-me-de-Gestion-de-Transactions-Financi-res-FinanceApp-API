package com.financeapp.controller;

import com.financeapp.entity.Account;
import com.financeapp.service.interfaces.AccountService;
import com.financeapp.service.interfaces.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts") 
@Validated
public class AccountController {
    
    private final AccountService accountService;
    private final UserService userService;
    
    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }
    
    @GetMapping
    public ResponseEntity<List<Account>> getUserAccounts() {
        try {
            List<Account> accounts = accountService.getUserAccounts(userService.getCurrentUser());
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(
            @PathVariable 
            @NotBlank(message = "Account ID is required")
            String accountId) {
        try {
            Account account = accountService.getAccountById(accountId);
            if (account == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Account> createAccount(
            @Valid 
            @RequestBody 
            AccountRequest request) {
        try {
            Account account = accountService.createAccount(
                userService.getCurrentUser(), 
                request.getType()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // DTO pour la requête de création
    public static class AccountRequest {
        private Account.AccountType type;
        
        public Account.AccountType getType() {
            return type;
        }
        
        public void setType(Account.AccountType type) {
            this.type = type;
        }
    }
}