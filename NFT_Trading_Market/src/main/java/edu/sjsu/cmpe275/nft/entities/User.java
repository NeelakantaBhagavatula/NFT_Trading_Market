package edu.sjsu.cmpe275.nft.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "USERNAME")
	private String email;

	@Column(name = "NICKNAME")
	private String nickName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "PROVIDER")
	private String provider;

	@Column(name = "ENABLED")
	private boolean enabled;

	@Column(name = "IS_VERIFIED")
	private boolean isVerified;
	
	@Column(name = "TOKEN")
	private String token;

	@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<NFT> nfts;

	@JsonManagedReference
	@OneToMany(mappedBy = "walletId.user")
	private List<Wallet> wallets;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "seller")
	private List<Sale> sellerSales;

	@JsonManagedReference
	@OneToMany(mappedBy = "buyer")
	private List<Sale> buyerSales;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<Bid> bids;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user")
	private List<Transaction> transactions;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long userId) {
		this.id = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<NFT> getNfts() {
		return nfts;
	}

	public void setNfts(List<NFT> nfts) {
		this.nfts = nfts;
	}

	public List<Wallet> getWallets() {
		return wallets;
	}

	public void setWallets(List<Wallet> wallets) {
		this.wallets = wallets;
	}

	public List<Sale> getSellerSales() {
		return sellerSales;
	}

	public void setSellerSales(List<Sale> sellerSales) {
		this.sellerSales = sellerSales;
	}

	public List<Sale> getBuyerSales() {
		return buyerSales;
	}

	public void setBuyerSales(List<Sale> buyerSales) {
		this.buyerSales = buyerSales;
	}

	public List<Bid> getBids() {
		return bids;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

}