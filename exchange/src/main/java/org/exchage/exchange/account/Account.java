package org.exchage.exchange.account;

public class Account {
	private long ownerId;
	private String currency;
	private double balance;

	public Account(long ownerId, String currency, double balance) {
		this.ownerId = ownerId;
		this.currency = currency;
		this.balance = balance;
	}

	public long getOwnerId() {
		return this.ownerId;
	}

	public String getCurrency() {
		return this.currency;
	}

	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
