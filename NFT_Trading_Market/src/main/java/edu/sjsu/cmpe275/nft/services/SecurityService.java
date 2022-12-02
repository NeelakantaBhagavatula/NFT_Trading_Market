package edu.sjsu.cmpe275.nft.services;

import java.util.Map;

import edu.sjsu.cmpe275.nft.entities.User;

// Interface for handling login and maintaining session
public interface SecurityService {

	boolean login(String email, String password);
	
	User getCurrentLoggedInUser();
	
	Map<String, Object> getCurrentLoggedInUserAttibutesFromOAuth();
	
	void removeCurrentLoggedInUserFromOAuth();
}
