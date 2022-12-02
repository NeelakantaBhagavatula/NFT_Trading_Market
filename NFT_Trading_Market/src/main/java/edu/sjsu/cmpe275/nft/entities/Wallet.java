package edu.sjsu.cmpe275.nft.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CRYPTOCURRENCY_WALLET")
public class Wallet {

	@EmbeddedId
	private WalletId walletId;

	@Column(name = "BALANCE")
	private Double balance;

	public WalletId getWalletId() {
		return walletId;
	}

	public void setWalletId(WalletId walletId) {
		this.walletId = walletId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}