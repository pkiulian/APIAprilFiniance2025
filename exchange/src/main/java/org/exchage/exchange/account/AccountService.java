package org.exchage.exchange.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;



/**
 * This service contains the business logic for transferring funds, creating accounts, and checking balances.
 */
@Service
public class AccountService {

    private final Map<Long, Account> accounts = new HashMap<>();

    // Method to transfer funds between accounts
    public boolean transferFunds(long debitAccountId, long creditAccountId, double amount, String debitCurrency, String creditCurrency) throws Exception {
        Account debitAccount = this.accounts.get(debitAccountId);
        Account creditAccount = this.accounts.get(creditAccountId);

        if (debitAccount == null || creditAccount == null) {
            throw new Exception("Either debit or credit account does not exist");
        }

        if (debitAccount.getBalance() < amount) {
            throw new Exception("Insufficient balance");
        }

        // Simulate an exchange rate between currencies (this can be an API call to a real exchange rate service)
        double exchangeRate = getExchangeRate(debitCurrency, creditCurrency);

        // Debit the account
        debitAccount.setBalance(debitAccount.getBalance() - amount);

        // Credit the account (after applying exchange rate)
        double creditedAmount = amount * exchangeRate;
        creditAccount.setBalance(creditAccount.getBalance() + creditedAmount);

        return true;
    }

    // Simulate fetching exchange rates (could call an external API here)
    private double getExchangeRate(String debitCurrency, String creditCurrency) {
        if (debitCurrency.equals("USD") && creditCurrency.equals("EUR")) {
            return 0.85;
        } else if (debitCurrency.equals("EUR") && creditCurrency.equals("USD")) {
            return 1.18;
        }
        return 1.0; // Default case, no exchange rate applied
    }

    // Create a new account
    public Account createAccount(Account account) {
        this.accounts.put(account.getOwnerId(), account);
        return account;
    }

    // Get account by ID
    public Account getAccount(long ownerId) {
        return this.accounts.get(ownerId);
    }
}