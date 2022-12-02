package edu.sjsu.cmpe275.nft.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TRANSACTION_ID")
	private Long id;
	
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	
	@Column(name = "TRANSACTION_DATE")
	private Timestamp transactionDate;
	
	@Column(name = "TRANSACTION_AMOUNT")
	private double transctionAmount;
	
	@Column(name = "REMAINDER_BALANCE")
	private double remainderBalance;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "TOKEN_ID")
	private NFT nft;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SYMBOL")
	private Cryptocurrency cryptocurrency;

	public Long getTransactionId() {
		return id;
	}

	public void setTransactionId(Long transactionId) {
		this.id = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Timestamp getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getTransctionAmount() {
		return transctionAmount;
	}

	public void setTransctionAmount(double transctionAmount) {
		this.transctionAmount = transctionAmount;
	}

	public double getRemainderBalance() {
		return remainderBalance;
	}

	public void setRemainderBalance(double remainderBalance) {
		this.remainderBalance = remainderBalance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public NFT getNft() {
		return nft;
	}

	public void setNft(NFT nft) {
		this.nft = nft;
	}

	public Cryptocurrency getCryptocurrency() {
		return cryptocurrency;
	}

	public void setCryptocurrency(Cryptocurrency cryptocurrency) {
		this.cryptocurrency = cryptocurrency;
	}
}
