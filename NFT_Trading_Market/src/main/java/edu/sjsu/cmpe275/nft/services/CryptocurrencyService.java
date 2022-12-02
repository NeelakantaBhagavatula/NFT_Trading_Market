package edu.sjsu.cmpe275.nft.services;

import java.util.List;

import edu.sjsu.cmpe275.nft.entities.Cryptocurrency;

public interface CryptocurrencyService {

	Cryptocurrency getBySymbol(String symbol);
	
	List<Cryptocurrency> getAll();
	
	Cryptocurrency addCryptocurrency(Cryptocurrency cryptocurrency);
	
	Cryptocurrency createCryptocurrency(String symbol, String name);
}
