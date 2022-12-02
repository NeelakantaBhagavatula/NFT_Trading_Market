package edu.sjsu.cmpe275.nft.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.nft.entities.Wallet;
import edu.sjsu.cmpe275.nft.entities.WalletId;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, WalletId> {

	@Query("from Wallet where wallet_id=:id")
	List<Wallet> findAllWalletsById(@Param("id") Long id);
	
	@Query("from Wallet where wallet_id=:id and symbol=:symbol")
	Wallet findByIdAnySymbol(@Param("id") Long id, @Param("symbol")String symbol);

}
