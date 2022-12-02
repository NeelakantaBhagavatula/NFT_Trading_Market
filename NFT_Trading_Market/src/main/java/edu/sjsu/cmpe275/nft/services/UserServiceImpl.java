package edu.sjsu.cmpe275.nft.services;

import java.net.InetAddress;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.nft.entities.User;
import edu.sjsu.cmpe275.nft.repos.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Value("${server.port}")
	private String PORT;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	@Transactional
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional
	public User addUser(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public User getUserByNickName(String nickname) {
		return userRepository.findByNickName(nickname);
	}

	@Async
	@Override
	public void sendEmailForVerification(User user) throws Exception {
		String host = InetAddress.getLocalHost().getHostName();
		MimeMessage message = mailSender.createMimeMessage();

		try {
//			host = InetAddress.getLocalHost().getHostName();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(user.getEmail());
			helper.setSubject("Registration Verification");
			helper.setText("Please click here http://localhost:" + PORT +"/confirmAccount?token=" + user.getToken() + " to verify your account.");
			mailSender.send(message);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	@Override
	@Transactional
	public User getByToken(String token) {
		return userRepository.findByToken(token);
	}
	
	@Override
	@Transactional
	public User getById( long userId ) {
		return userRepository.getReferenceById( userId );
	}

}
