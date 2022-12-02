package edu.sjsu.cmpe275.nft.services;

import edu.sjsu.cmpe275.nft.entities.Bid;
import edu.sjsu.cmpe275.nft.entities.Sale;

public interface SaleService {

	Sale getById(long saleId);

	Sale save(Sale sale);

	Bid getBidById(Long bidId);

	Bid saveBid(Bid bid);

	Bid getPreviousActiveBid(Bid newBid);

	Bid getHighestBid(Long saleId);
}
