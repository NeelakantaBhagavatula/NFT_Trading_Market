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
@Table(name = "SALE_BID")
public class Bid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BID_ID")
	private Long id;

	@Column(name = "BID_TIME")
	private Timestamp bidTime;

	@Column(name = "BID_VALUE")
	private double bidValue;

	@Column(name = "EXPIRATION_TIME")
	private Timestamp expirationTime;

	@Column(name = "WAS_ACCEPTED")
	private boolean wasAccepted;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "SALE_ID")
	private Sale sale;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "BIDDER_ID", referencedColumnName = "USER_ID")
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getBidTime() {
		return bidTime;
	}

	public void setBidTime(Timestamp bidTime) {
		this.bidTime = bidTime;
	}

	public double getBidValue() {
		return bidValue;
	}

	public void setBidValue(double bidValue) {
		this.bidValue = bidValue;
	}

	public Timestamp getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Timestamp expirationTime) {
		this.expirationTime = expirationTime;
	}

	public boolean isWasAccepted() {
		return wasAccepted;
	}

	public void setWasAccepted(boolean wasAccepted) {
		this.wasAccepted = wasAccepted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

}