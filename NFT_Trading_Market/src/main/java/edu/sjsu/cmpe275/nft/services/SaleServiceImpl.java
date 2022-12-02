package edu.sjsu.cmpe275.nft.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.nft.entities.Bid;
import edu.sjsu.cmpe275.nft.entities.Sale;
import edu.sjsu.cmpe275.nft.repos.BidRepository;
import edu.sjsu.cmpe275.nft.repos.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService {

	private static final Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);

	private final SaleRepository saleRepository;
	
	private final BidRepository bidRepository;
	
	@Autowired
	public SaleServiceImpl(SaleRepository saleRepository, BidRepository bidRepository ) {
		
		this.saleRepository = saleRepository;
		this.bidRepository = bidRepository;
	
	}
	
	@Override
	@Transactional
	public Sale getById( long saleId ) {
		
		return saleRepository.getReferenceById( saleId );
	}
	
	@Override
	@Transactional
	public Sale save(Sale sale) {
		return saleRepository.save( sale );
	}
	
	@Override
	@Transactional
	public Bid getBidById( Long bidId ) {
		return bidRepository.getReferenceById( bidId );
	}
	
	@Override
	@Transactional
	public Bid saveBid( Bid bid ) {
		return bidRepository.save( bid );
	}
	
	@Override
	@Transactional
	public Bid getPreviousActiveBid( Bid newBid ) {
		return bidRepository.findTopBySaleIdAndUserIdOrderByIdDesc( newBid.getSale().getId(), newBid.getUser().getId() );
	}
	
	@Override
	@Transactional
	public Bid getHighestBid( Long saleId ) {
		return bidRepository.findTopBySaleIdOrderByBidValueDesc( saleId );
	}

}