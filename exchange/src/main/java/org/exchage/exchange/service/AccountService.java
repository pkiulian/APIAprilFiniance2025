package org.exchage.exchange.service;

import java.util.Optional;

import org.exchage.exchange.account.Account;
import org.exchage.exchange.exceptions.InsufficientFundsException;
import org.exchage.exchange.rate.MockExchangeRateService;
import org.exchage.exchange.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.OptimisticLockException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;


	// Method to find an Account by Owner ID
	public Optional<Account> findByOwnerId(Long ownerId) {
		return this.accountRepository.findByOwnerId(ownerId);
	}

	// Method to save an Account
	public Account saveAccount(Account account) {
		return this.accountRepository.save(account);
	}

	// Method to update balance of an account
	public Account updateBalance(Long accountId, Double newBalance) {
		Optional<Account> accountOptional = this.accountRepository.findById(accountId);
		if (accountOptional.isPresent()) {
			Account account = accountOptional.get();
			account.setBalance(newBalance);
			return this.accountRepository.save(account);
		}
		return null;
	}

	// Ensures that all database operations inside the transferFunds method are
	// executed in a single transaction.
	@Transactional
	public boolean transferFunds(Long debitAccountId, Long creditAccountId, Double amount, String debitCurrency,
			String creditCurrency) {



		// Retrieve the debit and credit accounts
		Account debitAccount = this.accountRepository.findById(debitAccountId)
				.orElseThrow(() -> new RuntimeException("Debit account not found"));
		Account creditAccount = this.accountRepository.findById(creditAccountId)
				.orElseThrow(() -> new RuntimeException("Credit account not found"));

		 // Check for sufficient balance in the debit account
	    if (debitAccount.getBalance() < amount) {
	        throw new InsufficientFundsException("Insufficient funds in the debit account");
	    }

		// Check for sufficient balance in the debit account
		if (debitAccount.getBalance() < amount) {
			throw new RuntimeException("Insufficient funds in the debit account");
		}

		// Calculated from debit to credit.
		double exchangeRate = MockExchangeRateService.getExchangeRate(debitCurrency, creditCurrency);
		double convertedAmount = amount * exchangeRate;

		// Update balances
		debitAccount.setBalance(debitAccount.getBalance() - amount);
		creditAccount.setBalance(creditAccount.getBalance() + convertedAmount);

		try {
			// Save the accounts (optimistic locking will happen during save)
			this.accountRepository.save(debitAccount);
			this.accountRepository.save(creditAccount);
		} catch (OptimisticLockException e) {
			System.out.println("Optimistic Locking Failure: " + e.getMessage());
			throw new RuntimeException("Conflict detected, try again later.");
		}
		return true;
	}

	public boolean safeTransfer(Long debitAccountId, Long creditAccountId, Double amount, String debitCurrency,
			String creditCurrency) {
		int retries = 10;
		while (retries-- > 0) {
			try {
				return transferFunds(debitAccountId, creditAccountId, amount, debitCurrency, creditCurrency);
			} catch (ObjectOptimisticLockingFailureException e) {

				if (retries == 0) {
					throw e;
				}
				try {
					Thread.sleep(500); //wait for db to work
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					return false;
				}
			}
		}
		return false;
	}
}
