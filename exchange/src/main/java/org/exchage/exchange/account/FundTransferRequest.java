package org.exchage.exchange.account;

/**
 * This class represents the transfer request payload containing the debit and credit account IDs, amount, and currencies.
 */
public class FundTransferRequest {
    /**
     * The ID of the account from which funds will be debited
     */
    private long debitAccountId;
    /**
     * The ID of the account to which funds will be credited
     */
    private long creditAccountId;

    /**
     *  the amount of funds to be transferred
     */
    private double amount;
    /**
     *  the currency of the debit account
     */
    private String debitCurrency;
    /**
     * the currency of the credit account
     */
    private String creditCurrency;


    public long getDebitAccountId() {
        return this.debitAccountId;
    }

    public void setDebitAccountId(long debitAccountId) {
        this.debitAccountId = debitAccountId;
    }

    public long getCreditAccountId() {
        return this.creditAccountId;
    }

    public void setCreditAccountId(long creditAccountId) {
        this.creditAccountId = creditAccountId;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDebitCurrency() {
        return this.debitCurrency;
    }

    public void setDebitCurrency(String debitCurrency) {
        this.debitCurrency = debitCurrency;
    }

    public String getCreditCurrency() {
        return this.creditCurrency;
    }

    public void setCreditCurrency(String creditCurrency) {
        this.creditCurrency = creditCurrency;
    }
}
