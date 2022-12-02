package edu.sjsu.cmpe275.nft.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "nft")
@Table(name = "NFT")
public class NFT {

	@Id
	@Column(name = "TOKEN_ID")
	private String tokenId;
	
	@Column(name = "SMART_CONTRACT_ADDRESS")
	private String smartContractAddress;
	
	@Column(name = "LAST_RECORDED_DATE")
	private Timestamp lastRecordedDate;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	
	@Column(name = "ASSET_URL")
	private String assetUrl;
	
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	
	@JsonManagedReference
	@OneToMany(mappedBy = "nft")
	private List<Sale> sales;
	
	
	@JsonManagedReference
	@OneToMany(mappedBy = "nft")
	private List<Transaction> transactions;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getSmartContractAddress() {
		return smartContractAddress;
	}

	public void setSmartContractAddress(String smartContractAddress) {
		this.smartContractAddress = smartContractAddress;
	}

	public Timestamp getLastRecordedDate() {
		return lastRecordedDate;
	}

	public void setLastRecordedDate(Timestamp lastRecordedDate) {
		this.lastRecordedDate = lastRecordedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAssetUrl() {
		return assetUrl;
	}

	public void setAssetUrl(String assetUrl) {
		this.assetUrl = assetUrl;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

}