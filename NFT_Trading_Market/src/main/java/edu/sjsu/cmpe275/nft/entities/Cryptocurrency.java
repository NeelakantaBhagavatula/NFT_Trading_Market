package edu.sjsu.cmpe275.nft.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "CRYPTOCURRENCY")
public class Cryptocurrency {

	@Id
	@Column(name = "SYMBOL")
	private String symbol;
	
	@Column(name = "NAME")
	private String name;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "walletId.cryptocurrency")
	private List<Wallet> wallets;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "cryptocurrency")
	private List<Sale> sales;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "cryptocurrency")
	private List<Transaction> transactions;
	
	public Cryptocurrency() {
		
	}

	public Cryptocurrency(String symbol, String name) {
		this.symbol = symbol;
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Sale> getSale() {
		return sales;
	}

	public void setSale(List<Sale> sales) {
		this.sales = sales;
	}

	public List<Wallet> getWallets() {
		return wallets;
	}

	public void setWallets(List<Wallet> wallets) {
		this.wallets = wallets;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}