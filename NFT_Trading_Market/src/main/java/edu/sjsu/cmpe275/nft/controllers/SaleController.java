package edu.sjsu.cmpe275.nft.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.sjsu.cmpe275.nft.entities.Bid;
import edu.sjsu.cmpe275.nft.entities.NFT;
import edu.sjsu.cmpe275.nft.entities.Sale;
import edu.sjsu.cmpe275.nft.entities.User;
import edu.sjsu.cmpe275.nft.entities.enums.SalesType;
import edu.sjsu.cmpe275.nft.services.CryptocurrencyService;
import edu.sjsu.cmpe275.nft.services.SaleService;
import edu.sjsu.cmpe275.nft.services.UserService;

@Controller
@RequestMapping("/sale")
@CrossOrigin(origins = "*")
public class SaleController {

	private static final Logger LOG = LoggerFactory.getLogger(SaleController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SaleService saleService;
	
	@Autowired
	private CryptocurrencyService cryptocurrencyService;
	
	@GetMapping("/new/{token}")
	public String newSale( @PathVariable("token") String token, Model model ) {
		
		model.addAttribute( "cryptos", cryptocurrencyService.getAll( ) );
		model.addAttribute( "saleTypes", Arrays.asList( SalesType.values() ) );
		
		Sale sale = new Sale();
		
		// Maybe get user from session?
		User seller = userService.getById(1);
		
		NFT nft = new NFT();
		
		if( !nft.getUser().equals(seller) ) {
			
			model.addAttribute( "message", "NFT doesn't belong to user " + seller.getNickName() );
			
			return "saleMessage";
			
		}
		
		sale.setSeller( seller );
		sale.setNft( nft );
				
		model.addAttribute( "sale", sale );
		
		return "saleForm";
		
	}
	
	@PostMapping("/save")
	public String createSale( @ModelAttribute("sale") Sale sale ) {
		
		sale.setCreationTime( new Timestamp ( System.currentTimeMillis() ) );
		
		saleService.save(sale);
		
		return "saleSuccess";
		
	}
	
	@GetMapping("/bid/{saleId}")
	public String bidOnAuction( @PathVariable("saleId") Long saleId, Model model ) {
		
		String message = null;
		
		Bid bid = new Bid();
		
		// Maybe get user from session?
		User bidder = userService.getById(1);

		Sale sale = saleService.getById( saleId );
		
		if( sale.getType() == SalesType.PRICED ) {
			
			message = "It is not possible to bid in a Priced sale.";
			
		}
		
		if( sale.getSeller().getId() == bidder.getId() ) {
			
			message = "Seller cannot bid on his own NFT auction.";
			
		}
		
		if( sale.getClosingTime() != null ) {
			
			message = "It is not possible to bid in a closed auction.";
			
		}
		
		if( message != null ) {
			
			model.addAttribute( "message", message );
			
			return "saleMessage";
			
		}
		
		bid.setSale(sale);
		bid.setUser(bidder);
		
		model.addAttribute( "bid", bid );
		
		return "bidForm";
		
	}
	
	@PostMapping("/bid/makeBid")
	public String makeBid( @ModelAttribute("bid") Bid bid, Model model ) {
		
		Bid highestBid = saleService.getHighestBid( bid.getSale().getId() );
		
		if( highestBid != null ) {
			
			if( bid.getBidValue() <= highestBid.getBidValue() ) {
				
				model.addAttribute( "message", "Bid has to be greater then " + highestBid.getBidValue() + " " + highestBid.getSale().getCryptocurrency().getSymbol() );
				
				return "saleMessage";
				
			}
			
			highestBid.setExpirationTime(new Timestamp ( System.currentTimeMillis() ) );
			
			saleService.saveBid( highestBid );
			
		}
		
		Bid previousBid = saleService.getPreviousActiveBid( bid );
		
		if( previousBid != null ) {
			
			previousBid.setExpirationTime(new Timestamp ( System.currentTimeMillis() ) );
			
			saleService.saveBid( previousBid );
			
		}
		
		bid.setBidTime( new Timestamp ( System.currentTimeMillis() ) );
		
		saleService.saveBid( bid );
		
		return "saleSuccess";
		
	}
	
	@GetMapping("/bid/acceptOffer/{bidId}")
	public String acceptOffer( @PathVariable("bidId") Long bidId, Model model ) {
		
		Bid currentBid = saleService.getBidById( bidId );
		
		String message = null;
		
		if( currentBid == null ) {
			
			message = "Bid with ID " + bidId + " does not exist";
			
		} else if( currentBid.getSale().getClosingTime() != null ) {
			
			message = "It is not possible to accept offers for sales that have already been closed.";
			
		} else if( currentBid.getExpirationTime() != null && currentBid.getExpirationTime().before( new Timestamp( System.currentTimeMillis() ) ) ) {
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "mm/dd/yyyy hh:mm:ss" );
			
			message = "This offer has expired at " + simpleDateFormat.format( currentBid.getExpirationTime() );
			
		}
		
		if( message != null ) {
			
			model.addAttribute( "message", message );
			
			return "saleMessage";
			
		}
		
		// TODO Check if the user is the current owner of that Sale
		// TODO Check if the bid is the highest offer?
		// TODO Change the ownership of the NFT
		
		currentBid.getSale().setBuyer( currentBid.getUser() );
		currentBid.getSale().setReceivedValue( currentBid.getBidValue() );
		currentBid.getSale().setClosingTime( new Timestamp ( System.currentTimeMillis() ) );
		
		currentBid.setWasAccepted( true );
		
		saleService.saveBid( currentBid );
		
		return "saleSuccess";
		
	}
	
}