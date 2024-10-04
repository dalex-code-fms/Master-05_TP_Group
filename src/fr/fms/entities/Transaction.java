package fr.fms.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	private int idTransaction = 0;
	private String dateTransaction;
	private double amountTransaction;
	private Account account;
	private int accountId;

	public Transaction(Date dateTransaction, double amountTransaction, int accountId) {
		this.idTransaction++;
		this.dateTransaction = new SimpleDateFormat("yyyy-MM-dd").format(dateTransaction);
		this.amountTransaction = amountTransaction;
		this.accountId = accountId;
	}

	public Transaction(Date dateTransaction, double amountTransaction, Account account) {
		this.dateTransaction = new SimpleDateFormat("yyyy-MM-dd").format(dateTransaction);
		this.amountTransaction = amountTransaction;
		this.account = account;
	}

	// Getters et Setters
	public void setAccount(Account account) {
		this.account = account;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return String.format("Transaction [transactionId=%d, transactionDate=%s, amount=%.2f, accountId=%d",
				idTransaction, dateTransaction, amountTransaction, accountId);
	}
}
