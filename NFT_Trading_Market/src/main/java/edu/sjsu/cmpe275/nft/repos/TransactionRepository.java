package edu.sjsu.cmpe275.nft.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.sjsu.cmpe275.nft.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	@Query("from Transaction where user_id=:userId")
	List<Transaction> findAllByUserId(@Param("userId") Long userId);
	
//	@Query("from Transaction where user_id=:userId and symbol=:currency and transaction_date > clock_timestamp() - make_interval(days => :days)")
//	List<Transaction> filterTransactions(@Param("userId") Long userId, @Param("currency") String currency, @Param("days") int days);
	
	@Query(value = "from Transaction where user_id=:userId and symbol=:currency and transaction_date >= DATE_SUB(CURDATE(), INTERVAL 1 DAY)", nativeQuery = true)
	List<Transaction> filterLastOneDayTransactions(@Param("userId") Long userId, @Param("currency") String currency);
	
	@Query(value = "from Transaction where user_id=:userId and symbol=:currency and transaction_date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)", nativeQuery = true)
	List<Transaction> filterLastOneWeekTransactions(@Param("userId") Long userId, @Param("currency") String currency);
	
	@Query(value = "from Transaction where user_id=:userId and symbol=:currency and transaction_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)", nativeQuery = true)
	List<Transaction> filterLastOneMonthTransactions(@Param("userId") Long userId, @Param("currency") String currency);
}
