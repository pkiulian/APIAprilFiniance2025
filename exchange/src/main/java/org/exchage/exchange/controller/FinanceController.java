package org.exchage.exchange.controller;

import org.exchage.exchange.account.Account;
import org.exchage.exchange.account.AccountService;
import org.exchage.exchange.account.FundTransferRequest;
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
            boolean success = this.accountService.transferFunds(
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

    // Create an account
    @PostMapping("/createAccount")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = this.accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    // Get account details
    @GetMapping("/account/{ownerId}")
    public ResponseEntity<Account> getAccount(@PathVariable long ownerId) {
        Account account = this.accountService.getAccount(ownerId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }
}