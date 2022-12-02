package edu.sjsu.cmpe275.nft.services;

import java.util.List;

import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.nft.entities.Cryptocurrency;

import edu.sjsu.cmpe275.nft.repos.CryptocurrencyRepository;

@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

	private static final Logger log = LoggerFactory.getLogger(CryptocurrencyServiceImpl.class);

	private final CryptocurrencyRepository cryptocurrencyRepository;

	@Autowired
	public CryptocurrencyServiceImpl(CryptocurrencyRepository cryptocurrencyRepository) {
		this.cryptocurrencyRepository = cryptocurrencyRepository;
	}

	@Override
	@Transactional
	public Cryptocurrency getBySymbol(String symbol) {
		return cryptocurrencyRepository.getReferenceById(symbol);
	}

	@Override
	@Transactional
	public List<Cryptocurrency> getAll() {
		return cryptocurrencyRepository.findAll();
	}

	@Override
	@Transactional
	public Cryptocurrency addCryptocurrency(Cryptocurrency cryptocurrency) {
		return cryptocurrencyRepository.save(cryptocurrency);
	}
	
	@Override
	@Transactional
	public Cryptocurrency createCryptocurrency(String symbol, String name) {
		Cryptocurrency cryptocurrency = new Cryptocurrency(symbol, name);
		return cryptocurrencyRepository.save(cryptocurrency);
	}

}