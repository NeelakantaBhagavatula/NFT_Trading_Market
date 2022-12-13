package edu.sjsu.cmpe275.nft.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.nft.entities.Bid;
import edu.sjsu.cmpe275.nft.entities.Sale;
import edu.sjsu.cmpe275.nft.entities.User;
import edu.sjsu.cmpe275.nft.repos.BidRepository;
import edu.sjsu.cmpe275.nft.repos.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService {

	private static final Logger log = LoggerFactory.getLogger(SaleServiceImpl.class);

	private final SaleRepository saleRepository;
	
	private final BidRepository bidRepository;
	
	private final SecurityService securityService;
	
	@Autowired
	public SaleServiceImpl(SaleRepository saleRepository, BidRepository bidRepository, SecurityService securityService ) {
		
		this.saleRepository = saleRepository;
		this.bidRepository = bidRepository;
		this.securityService = securityService;
	
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
	public Bid getPreviousActiveBid( Long saleId, Long userId ) {
		return bidRepository.findTopBySaleIdAndUserIdOrderByIdDesc( saleId, userId );
	}
	
	@Override
	@Transactional
	public Bid getHighestBid( Long saleId ) {
		return bidRepository.findTopBySaleIdOrderByBidValueDesc( saleId );
	}
	
	@Override
	@Transactional
	public List<Sale> getAllSalesListedBy(User user) {
		Long userId = user.getId();
		return saleRepository.findAllSalesListedBy(userId);
	}
	
	@Override
	@Transactional
	public List<Sale> getOpened( ) {
		
		User user = securityService.getCurrentLoggedInUser();
		
		return saleRepository.findAllOpenedSales( user.getId() );
		
	}

}