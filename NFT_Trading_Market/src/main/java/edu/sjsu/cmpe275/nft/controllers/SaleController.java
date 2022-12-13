package edu.sjsu.cmpe275.nft.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.sjsu.cmpe275.nft.entities.Bid;
import edu.sjsu.cmpe275.nft.entities.Cryptocurrency;
import edu.sjsu.cmpe275.nft.entities.NFT;
import edu.sjsu.cmpe275.nft.entities.Sale;
import edu.sjsu.cmpe275.nft.entities.User;
import edu.sjsu.cmpe275.nft.entities.Wallet;
import edu.sjsu.cmpe275.nft.entities.enums.SalesType;
import edu.sjsu.cmpe275.nft.services.CryptocurrencyService;
import edu.sjsu.cmpe275.nft.services.NFTService;
import edu.sjsu.cmpe275.nft.services.SaleService;
import edu.sjsu.cmpe275.nft.services.SecurityService;
import edu.sjsu.cmpe275.nft.services.WalletService;

@Controller
@RequestMapping("/sale")
@CrossOrigin(origins = "*")
public class SaleController {

	private static final Logger LOG = LoggerFactory.getLogger(SaleController.class);
	
	@Autowired
	private SaleService saleService;
	
	@Autowired
	private CryptocurrencyService cryptocurrencyService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private NFTService nftService;
	
	@Autowired
	private WalletService walletService;
	
	@GetMapping("/new/{token}")
	public String newSale( @PathVariable("token") String token, ModelMap model ) {
		
		String message = null;
		
		model.addAttribute( "cryptos", cryptocurrencyService.getAll( ) );
		model.addAttribute( "saleTypes", Arrays.asList( SalesType.values() ) );

		User seller = securityService.getCurrentLoggedInUser();
		
		NFT nft = nftService.getNFTById( token );
		
		if( nft == null || !nft.getUser().equals(seller) ) {
			
			message = "NFT doesn't exist or doesn't belong to user " + seller.getNickName();
			
		} else if ( hasOpenSale( nft ) ) {
			
			message = "It's not possible to have open sales from the same NFT at same time.";
			
		}
		
		if( message != null ) {
			
			model.addAttribute( "msg", message );
			
			return listMyNFT( model );
			
		} 
		
		model.addAttribute( "sale", new Sale( seller, nft ) );
		
		return "saleForm";
		
	}
	
	@PostMapping("/save")
	public String createSale( @ModelAttribute("sale") Sale sale, ModelMap model ) {
		
		sale.setCreationTime( new Timestamp ( System.currentTimeMillis() ) );
		
		saleService.save(sale);
		
		model.addAttribute("msg", "Your sale was successfully created");
		
        return mySales( model );
		
	}
	
	@GetMapping("/makeOffer/{saleId}")
	public ModelAndView makeOffer( @PathVariable("saleId") Long saleId, ModelMap model ) {
		
		String forward = null;
		
		Sale sale = saleService.getById( saleId );
		
		if( sale.getType() == SalesType.PRICED  ) {
			
			forward = "forward:/sale/buy/" + sale.getId();
			
		} else {
			
			forward = "forward:/sale/bid/" + sale.getId();
			
		}
		
		return new ModelAndView( forward, model );
		
    }
	
	@GetMapping("/buy/{saleId}")
	public String buyNft( @PathVariable("saleId") Long saleId, ModelMap model ) {
		
		User buyer = securityService.getCurrentLoggedInUser();

		Sale sale = saleService.getById( saleId );
		
		String message = checkPurchase(sale, buyer);
		
		if( message != null ) {
			
			model.addAttribute( "msg", message );
			
			return openedSale( model );
			
		} 
		
		model.addAttribute( "sale", sale );
			
		return "salePurchaseConfirm";
	
	}
	
	@GetMapping("/bid/{saleId}")
	public String bidOnAuction( @PathVariable("saleId") Long saleId, ModelMap model ) {
		
		String message = null;
		
		User bidder = securityService.getCurrentLoggedInUser();

		Sale sale = saleService.getById( saleId );
		
		if( sale.getSeller().getId() == bidder.getId() ) {
			
			message = "Seller cannot bid on his own NFT auction.";
			
		} else if( sale.getType() == SalesType.PRICED ) {
			
			message = "It is not possible to bid in a Priced sale.";
			
		} else if( sale.getClosingTime() != null ) {

			message = "It is not possible to bid in a closed auction.";
			
		}
		
		if( message != null ) {
			
			model.addAttribute( "msg", message );
			
			return openedSale( model );
			
		}
		
		double minOffer = sale.getExpectedValue();
		
		Bid bid = getHighestActiveBid( sale.getBids() );
		
		if( bid != null ) {
			
			minOffer = bid.getBidValue();
			
		}
		
		model.addAttribute( "saleId", sale.getId() );
		model.addAttribute( "minPrice", minOffer );
		
		return "bidForm";
		
	}
	
	@GetMapping("/purchase/{saleId}")
	public String purchaseNft( @PathVariable("saleId") Long saleId, ModelMap model ) {
		
		Sale sale = saleService.getById( saleId );
		
		User buyer = securityService.getCurrentLoggedInUser();
		
		String message = checkPurchase(sale, buyer);
		
		if( message != null ) {
			
			model.addAttribute( "msg", message );
			
			return openedSale( model );
			
		} 
		
		sale.setClosingTime( new Timestamp ( System.currentTimeMillis() ) );
		
		sale.setBuyer( buyer );
		
		sale.setReceivedValue( sale.getExpectedValue() );
		
		//Update the wallets balance
		updateWallet( sale );
		
		// Changing the ownership of the NFT
		sale.getNft().setLastRecordedDate( new Timestamp( System.currentTimeMillis() ) );
		sale.getNft().setUser( securityService.getCurrentLoggedInUser() );
		sale.getNft().setSmartContractAddress( UUID.randomUUID().toString() );
		
		saleService.save( sale );
		
		model.addAttribute("msg", "Your purchase has been successfully completed.");
		
        return listMyNFT( model );
		
	}
	
	@PostMapping("/saveOffer")
	public String makeBid( @RequestParam("saleId") Long saleId, @RequestParam("bidValue") double bidValue,  
			               @RequestParam("hour") int hour, @RequestParam("minute") int minute, @RequestParam("second") int second, ModelMap model ) {
		
		String message = null;
		
		Sale sale = saleService.getById( saleId );
		
		User bidder = securityService.getCurrentLoggedInUser();
		
		Bid highestBid = getHighestActiveBid( sale.getBids() );
		
		if( highestBid != null ) {
			
			if( bidValue <= highestBid.getBidValue() ) {
				
				message = "Bid has to be greater then " + highestBid.getBidValue() + " " + highestBid.getSale().getCryptocurrency().getSymbol();
				
			}
			
		} 
		
		if( hour == 0 && minute == 0 && second == 0 ) {
			
			message = "You need to set the expiration time. Hour, minute, or second must be greater than zero.";
			
		} 
		
		if( !hasEnoughtBalance( sale, bidder, bidValue ) ) {
			
			message = "You don't have enough " + sale.getCryptocurrency().getName() + " balance for this purchase."; 
			
		}
		
		if( message != null ) {
			
			model.addAttribute( "msg", message );
			
			return bidOnAuction( saleId, model );
			
		}
		
		Bid previousBid = saleService.getPreviousActiveBid( sale.getId(), bidder.getId() );
		
		if( previousBid != null ) {
			
			previousBid.setExpirationTime( new Timestamp ( System.currentTimeMillis() ) );
			
			saleService.saveBid( previousBid );
			
		}
		
		Bid newBid = new Bid();
		
		Timestamp expiration = new Timestamp ( System.currentTimeMillis() );
		
		expiration.setTime( expiration.getTime() + ( ( ( hour * 60 * 60 ) + ( minute * 60 ) + second ) * 1000 ) );
		
		newBid.setBidTime( new Timestamp ( System.currentTimeMillis() ) );
		newBid.setBidValue( bidValue );
		newBid.setUser( bidder );
		newBid.setSale( sale );
		newBid.setExpirationTime( expiration );
		
		saleService.saveBid( newBid );
		
		model.addAttribute( "msg", "Your offer has been sent successfully." );
		
		return openedSale( model ) ;
		
	}
	
	@GetMapping("/acceptOffer/{bidId}")
	public String acceptOffer( @PathVariable("bidId") Long bidId, ModelMap model ) {
		
		Bid currentBid = saleService.getBidById( bidId );
		
		String message = null;
		
		if( currentBid == null ) {
			
			message = "Bid with ID " + bidId + " does not exist";
			
		} else if( !currentBid.getSale().getSeller().equals( securityService.getCurrentLoggedInUser() ) ) {
		
			message = "You can only accept offers from your own sales.";
			
		} else if( currentBid.getSale().getClosingTime() != null ) {
			
			message = "It's not possible to accept offers for sales that have already been closed.";
			
		} else if( currentBid.getExpirationTime() != null && currentBid.getExpirationTime().before( new Timestamp( System.currentTimeMillis() ) ) ) {
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "mm/dd/yyyy hh:mm:ss" );
			
			message = "This offer has expired at " + simpleDateFormat.format( currentBid.getExpirationTime() );
			
		}
		
		if( message == null ) {
			
			// Changing the ownership of the NFT
			currentBid.getSale().getNft().setLastRecordedDate( new Timestamp( System.currentTimeMillis() ) );
			currentBid.getSale().getNft().setUser( currentBid.getUser() );
			currentBid.getSale().getNft().setSmartContractAddress( UUID.randomUUID().toString() );
			
			currentBid.getSale().setBuyer( currentBid.getUser() );
			currentBid.getSale().setReceivedValue( currentBid.getBidValue() );
			currentBid.getSale().setClosingTime( new Timestamp ( System.currentTimeMillis() ) );
			
			currentBid.setExpirationTime( new Timestamp( System.currentTimeMillis() ) );
			
			for( Bid aux : currentBid.getSale().getBids() ) {
				
				if( aux.getExpirationTime().after( currentBid.getExpirationTime() ) ) {
					
					aux.setExpirationTime( currentBid.getExpirationTime() );
					
				}
				
			}
			
			//Update the wallets balance
			updateWallet( currentBid.getSale() );
			
			currentBid.setWasAccepted( true );
			
			saleService.saveBid( currentBid );
			
			message = "Your sale has been successfully completed.";
			
		}

		model.addAttribute( "msg", message );
		
		return mySales( model );
		
	}
	
	@GetMapping("/cancel/{saleId}")
	public ModelAndView cancelSale( @PathVariable("saleId") Long saleId, ModelMap model ) {
		
		String forward = null;
		
		Sale sale = saleService.getById( saleId );
		
		if( sale.getType() == SalesType.PRICED  ) {
			
			forward = "forward:/sale/cancel/priced/" + sale.getId();
			
		} else {
			
			forward = "forward:/sale/cancel/auction/" + sale.getId();
			
		}
		
		return new ModelAndView( forward, model );
		
	}
	
	@GetMapping("/cancel/priced/{saleId}")
	public String cancelPricedSale( @PathVariable("saleId") Long saleId, ModelMap model ) {
		
		String message = null;
		
		Sale sale = saleService.getById( saleId );
		
		if( sale.getClosingTime() != null ) {
			
			message = "It is not possible to cancel a sale that has already ended.";
			
		}
		
		if( message == null ) {
			
			sale.setClosingTime( new Timestamp ( System.currentTimeMillis() ) );
			
			message = "Your sale has been canceled successfully.";
			
		}
		
		model.addAttribute( "msg", message );
		
		return mySales( model );
		
	}
	
	@GetMapping("/cancel/auction/{saleId}")
	public String cancelAuctionSale( @PathVariable("saleId") Long saleId, ModelMap model ) {
		
		String message = null;
		
		Sale sale = saleService.getById( saleId );
		
		if( sale.getClosingTime() != null ) {
			
			message = "It is not possible to cancel a sale that has already ended.";
			
		}
		
		if( message == null ) {
			
			Bid bid = getHighestActiveBid( sale.getBids() );
			
			if( bid != null ) {
				
				return acceptOffer( bid.getId(), model );
				
			}
			
			sale.setClosingTime( new Timestamp ( System.currentTimeMillis() ) );
			
			message = "Your sale has been canceled successfully.";
			
		}
		
		model.addAttribute( "msg", message );
		
		return mySales( model );
		
	}
	
	@GetMapping("/cancel/bid/{bidId}")
	public String cancelBid( @PathVariable("bidId") Long bidId, ModelMap model ) {
		
		String message = null;
		
		Bid bid = saleService.getBidById( bidId );
		
		Bid highestBid = getHighestActiveBid( bid.getSale().getBids() );
		
		if( bid.equals(highestBid) ) {
			
			message = "It's not possible to cancel the highest bid.";
			
		}
		
		if( message == null ) {
			
			bid.setExpirationTime( new Timestamp ( System.currentTimeMillis() ) );
			
			saleService.saveBid( bid );
			
			message = "Your bid has been canceled successfully.";
			
		}
		
		model.addAttribute( "msg", message );
		
		return openedSale( model );
		
	}
	
	@GetMapping("/listOpened")
	public String openedSale( ModelMap modelMap) {
		
		try {
		    Thread.sleep( 500 );
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		}
		
		List<Sale> sales = saleService.getOpened( );
		
		modelMap.addAttribute( "userId", securityService.getCurrentLoggedInUser().getId() );
		modelMap.addAttribute( "sales", sales );
		
		return "listSaleOpened";
		
	}

	@GetMapping( )
	public String mySales( ModelMap modelMap) {
		
		List<Sale> sales = saleService.getAllSalesListedBy( securityService.getCurrentLoggedInUser() );
		
		for ( Sale sale : sales ) {
			
			if( sale.getType() == SalesType.AUCTION && sale.getBids() != null) {
				
				if( sale.getClosingTime() == null ) {
					
					sale.setBids( Arrays.asList( getHighestActiveBid( sale.getBids() ) ) );
					
				} else if( sale.getClosingTime() != null ) {
					
					if( sale.getBuyer() != null ) {
						
						for( Bid bid : sale.getBids() ) {
							
							if( bid.isWasAccepted() ) {
								
								sale.setBids( Arrays.asList( bid ) );
								
								break;
								
							}
							
						}
						
					} else {
						
						sale.setBids( null );
						
					}
					
				}
				
			}
			
		}
		
		modelMap.addAttribute( "sales", sales );
		
		return "browseNftsListedForSale";
		
	}
	
	private boolean hasEnoughtBalance( Sale sale, User buyer, double price ) {
		
		System.out.println( "Checking hasEnoughtBalance" );
		
		List<Wallet> wallets = buyer.getWallets();
		
			for( Wallet wallet : wallets ) {
					
				if( wallet.getWalletId().getCryptocurrency().equals( sale.getCryptocurrency() ) ) {
					
					System.out.println( "Checking hasEnoughtBalance: " + sale.getCryptocurrency().getSymbol() );
					
					if( ( wallet.getBalance() - totalCommittedInAuctions( buyer, sale ) ) < price ) { return false; }
					
					break;
						 
				 }
					
			}
		
		return true;
		
	}
	
	private double totalCommittedInAuctions( User buyer, Sale sale ) {
		
		List<Bid> allBids = buyer.getBids();
		
		double totalCommittedToBids = 0;
		
		for( Bid bid : allBids ) {
		
			if( !bid.getSale().equals(sale) && bid.getExpirationTime().after( new Timestamp( System.currentTimeMillis() ) ) && bid.getSale().getCryptocurrency().equals( sale.getCryptocurrency() ) ) {
				
				totalCommittedToBids += bid.getBidValue();
				
			}
			
		}
		
		System.out.println( "Commited:" + totalCommittedToBids );
		
		return totalCommittedToBids;
		
	}
	
	private String listMyNFT(ModelMap modelMap) {
		
		modelMap.addAttribute("nfts", nftService.getAllNFTs( securityService.getCurrentLoggedInUser() ) );
		
		return "displayNfts";
		
	}
	
	private void updateWallet( Sale sale ) {
		
		walletService.addToBalance( sale.getSeller().getId(), sale.getCryptocurrency().getSymbol(), sale.getReceivedValue() );
		
		walletService.subtractFromBalance( sale.getBuyer().getId(), sale.getCryptocurrency().getSymbol(), sale.getReceivedValue() );
		
	}
	
	private Bid getHighestActiveBid( List<Bid> bids ) {
		
		int lastPos = bids.size() - 1;
		
		while( lastPos >= 0 ) {
			
			if( bids.get(lastPos).getExpirationTime().after( new Timestamp ( System.currentTimeMillis() ) ) ) {
				
				return bids.get(lastPos);
				
			}
			
			lastPos--;
			
		}
		
		return null;
		
	}
	
	private String checkPurchase( Sale sale, User buyer ) {
		
		String message = null;
		
		if( sale.getSeller().getId() == buyer.getId() ) {
			
			message = "Seller cannot buy his own NFT.";
			
		} else if( sale.getType() == SalesType.AUCTION ) {
			
			message = "It isn't possible to buy a NFT from an Auction sale.";
			
		} else if( sale.getClosingTime() != null ) {

			message = "It isn't possible to buy NFT from a closed sale.";
			
		} else if( ! hasEnoughtBalance( sale, buyer, sale.getExpectedValue() ) ) {
			
			message = "You don't have enough " + sale.getCryptocurrency().getName() + " balance for this purchase."; 
			
		}
		
		return message;
		
	}
	
	private boolean hasOpenSale( NFT nft ) { 
		
		int lastPos = nft.getSales().size() - 1;
		
		if( lastPos >= 0 && nft.getSales().get( lastPos ).getClosingTime() == null ) {
			
			return true;
			
		}
		
		return false;
		
	}
	
}