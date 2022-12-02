package edu.sjsu.cmpe275.nft.services;

import edu.sjsu.cmpe275.nft.entities.User;

public interface UserService {

	User getUserByEmail(String email);
	
	User addUser(User user);
	
	User getUserByNickName(String nickName);
	
	void sendEmailForVerification(User user) throws Exception;
	
	User getByToken(String token);
	
	User getById( long userId );
}
