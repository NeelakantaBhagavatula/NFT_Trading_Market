package edu.sjsu.cmpe275.nft.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/home")
	public String getHome() {
		LOG.info("Executing getHome() << ");
		return "home";
	}
}
