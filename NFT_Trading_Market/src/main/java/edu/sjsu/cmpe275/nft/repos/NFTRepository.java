package edu.sjsu.cmpe275.nft.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.nft.entities.NFT;

@Repository
public interface NFTRepository extends JpaRepository<NFT, String> {

	@Query("from nft where user_id=:userId")
	public List<NFT> findAllNFTs(@Param("userId") Long userId);
}
