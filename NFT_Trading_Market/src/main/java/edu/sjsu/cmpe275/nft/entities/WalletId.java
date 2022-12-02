package edu.sjsu.cmpe275.nft.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Embeddable
public class WalletId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "WALLET_ID", referencedColumnName = "USER_ID")
	private User user;

	@ManyToOne
	@JoinColumn(name = "SYMBOL", referencedColumnName = "SYMBOL")
	private Cryptocurrency cryptocurrency;

	public WalletId() {

	}

	public WalletId(User user, Cryptocurrency cryptocurrency) {
		this.user = user;
		this.cryptocurrency = cryptocurrency;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Cryptocurrency getCryptocurrency() {
		return cryptocurrency;
	}

	public void setCryptocurrency(Cryptocurrency cryptocurrency) {
		this.cryptocurrency = cryptocurrency;
	}

	// TO-DO equals() and hashCode()
	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		WalletId walletId = (WalletId) o;

		return this.user.equals(walletId.getUser()) && this.cryptocurrency.equals(walletId.getCryptocurrency());
	}

	@Override
	public int hashCode() {

		return Objects.hash(this.user, this.cryptocurrency);

	}
}