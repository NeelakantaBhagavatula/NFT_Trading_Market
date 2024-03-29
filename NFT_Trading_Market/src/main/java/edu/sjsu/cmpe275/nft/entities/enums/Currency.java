package edu.sjsu.cmpe275.nft.entities.enums;

public enum Currency {

	BTC("BTC", "BITCOIN"),
	ETH("ETH", "ETHEREUM");
	
	private final String key;
	private final String value;

	Currency(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getValue() {
		return this.value;
	}
}
