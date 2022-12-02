package edu.sjsu.cmpe275.nft.services;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.nft.entities.NFT;
import edu.sjsu.cmpe275.nft.entities.User;
import edu.sjsu.cmpe275.nft.repos.NFTRepository;

@Service
public class NFTServiceImpl implements NFTService {

	private static final Logger log = LoggerFactory.getLogger(NFTServiceImpl.class);

	private final NFTRepository nftRepository;

	@Autowired
	public NFTServiceImpl(NFTRepository nftRepository) {
		this.nftRepository = nftRepository;
	}

	/**
	 * Returns newly saved NFT into database
	 * 
	 * @param nft NFT entity to be saved
	 * @return
	 */

	@Override
	@Transactional
	public NFT addNFT(NFT nft) {
		return nftRepository.save(nft);
	}
	
	@Override
	@Transactional
	public List<NFT> getAllNFTs(User user) {
		Long userId = user.getId();
		return nftRepository.findAllNFTs(userId);
	}
}
