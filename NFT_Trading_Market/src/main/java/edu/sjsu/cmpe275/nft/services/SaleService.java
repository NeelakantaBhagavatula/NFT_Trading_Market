package edu.sjsu.cmpe275.nft.services;

import java.util.List;

import edu.sjsu.cmpe275.nft.entities.Bid;
import edu.sjsu.cmpe275.nft.entities.Sale;
import edu.sjsu.cmpe275.nft.entities.User;

public interface SaleService {

	Sale getById(long saleId);

	Sale save(Sale sale);

	Bid getBidById(Long bidId);

	Bid saveBid(Bid bid);

	Bid getPreviousActiveBid(Long saleId, Long userId);

	Bid getHighestBid(Long saleId);
	
	List<Sale> getAllSalesListedBy(User user);
	
	List<Sale> getOpened( );
	
}