package edu.sjsu.cmpe275.nft.services;

import java.util.List;

import edu.sjsu.cmpe275.nft.entities.Cryptocurrency;
import edu.sjsu.cmpe275.nft.entities.User;
import edu.sjsu.cmpe275.nft.entities.Wallet;

public interface WalletService {

	Wallet create(Wallet wallet);
	
	void createWallets(User user);
	
	List<Wallet> getWallets(User user);
	
	Wallet getWallet(Long id, String symbol);
	
	Wallet updateWallet(Wallet wallet);
	
	List<Wallet> getWallets(Long id);
	
	void addToBalance( Long id, String symbol, double value );
	
	void subtractFromBalance( Long id, String symbol, double value );
	
	double getTotalCommittedInAuctions( User user, Cryptocurrency cryptocurrency );
	
}
