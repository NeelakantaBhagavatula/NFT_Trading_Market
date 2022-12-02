package edu.sjsu.cmpe275.nft.controllers;

import java.sql.Timestamp;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.sjsu.cmpe275.nft.entities.NFT;
import edu.sjsu.cmpe275.nft.entities.User;
import edu.sjsu.cmpe275.nft.services.NFTService;
import edu.sjsu.cmpe275.nft.services.UserService;

@Controller
public class NFTController {

	private static final Logger Log = LoggerFactory.getLogger(NFTController.class);

	@Autowired
	private NFTService nftService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/createnft")
	public String getcreateNft() {
		return "createNft";
	}
	

	@RequestMapping(value = "/createnft", method = RequestMethod.POST)
	public String createNFT(@ModelAttribute("nft") NFT nft, ModelMap modelMap, @CurrentSecurityContext(expression="authentication") Authentication auth) {
		
		System.out.print("In Create NFT api");
		
		Log.info("Executing createNft() {}, {}, {},{},{} ", nft.getName(), nft.getType(), nft.getDescription(),
				nft.getImageUrl(), nft.getAssetUrl());
		
		if (auth != null) {
		    String currentUserName = auth.getName();  
		    System.out.println("Current UserName"+ currentUserName);
		   User user= userService.getUserByEmail(currentUserName);
		   
		   
		   if(user != null) {
			   String tokenId = UUID.randomUUID().toString();

				Timestamp lastRecordedData = new Timestamp(System.currentTimeMillis());

				String smartContactAddress = UUID.randomUUID().toString();
				

				nft.setTokenId(tokenId);
				nft.setSmartContractAddress(smartContactAddress);
				nft.setLastRecordedDate(lastRecordedData);
				nft.setUser(user);

				nftService.addNFT(nft);
				
				modelMap.addAttribute("msg", "Successfully created NFT!");
				return "profile";
		   
		   }
		   
		   return "redirect:/";
		   
		   
		}
		
		
		return "redirect:/";
	}
	
}
