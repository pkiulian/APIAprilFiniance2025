package org.exchage.exchange.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incremented by the database
	@Column(name = "account_id")
	private long accountId;

	@Column(name = "owner_id", nullable = false)
	private long ownerId;

	@Column(name = "currency", nullable = false)
	private String currency;

	@Column(name = "balance", nullable = false)
	private double balance;


	//  used for optimistic locking.
	@Version
	@Column(name = "version")
	private long version;


	// Default constructor (no-argument constructor)
	public Account() {
	}

	public Account(long ownerId, String currency, double balance) {
		this.ownerId = ownerId;
		this.currency = currency;
		this.balance = balance;
	}
}
