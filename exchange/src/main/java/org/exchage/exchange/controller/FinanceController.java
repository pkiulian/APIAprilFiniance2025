package org.exchage.exchange.controller;

import java.util.Optional;

import org.exchage.exchange.account.Account;
import org.exchage.exchange.account.FundTransferRequest;
import org.exchage.exchange.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/finance")
public class FinanceController {

    private final AccountService accountService;

    @Autowired
    public FinanceController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody FundTransferRequest request) {
        try {
            boolean success = this.accountService.safeTransfer(
                    request.getDebitAccountId(),
                    request.getCreditAccountId(),
                    request.getAmount(),
                    request.getDebitCurrency(),
                    request.getCreditCurrency()
            );

            if (success) {
                return ResponseEntity.ok("Funds transferred successfully");
            } else {
                return ResponseEntity.badRequest().body("Transfer failed");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Create a new account
    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = this.accountService.saveAccount(account);
        return ResponseEntity.status(201).body(createdAccount);
    }

    @GetMapping("/account/{ownerId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long ownerId) {
        Optional<Account> account = this.accountService.findByOwnerId(ownerId);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }
}