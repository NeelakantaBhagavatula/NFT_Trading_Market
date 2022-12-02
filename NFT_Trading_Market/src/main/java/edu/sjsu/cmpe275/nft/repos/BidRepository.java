package edu.sjsu.cmpe275.nft.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.nft.entities.Bid;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
	
	Bid findTopBySaleIdAndUserIdOrderByIdDesc( Long saleId, Long userId );
	
	Bid findTopBySaleIdOrderByBidValueDesc( Long saleId );
	
}
