
package edu.sjsu.cmpe275.nft.services;

import java.util.List;

import edu.sjsu.cmpe275.nft.entities.NFT;
import edu.sjsu.cmpe275.nft.entities.User;

public interface NFTService {

	NFT addNFT(NFT nft);
	
	List<NFT> getAllNFTs(User user);

}
