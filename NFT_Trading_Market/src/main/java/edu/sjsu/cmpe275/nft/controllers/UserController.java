package edu.sjsu.cmpe275.nft.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.cmpe275.nft.entities.User;
import edu.sjsu.cmpe275.nft.entities.Wallet;
import edu.sjsu.cmpe275.nft.entities.enums.Provider;
import edu.sjsu.cmpe275.nft.services.SecurityService;
import edu.sjsu.cmpe275.nft.services.UserService;
import edu.sjsu.cmpe275.nft.services.WalletService;

@Controller
@CrossOrigin(origins = "*")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	// Regex which allows alphanumeric passwords only
	private static final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9]+$";

	@Autowired
	private UserService userService;

	// Bean for password encryption
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Bean for security (login)
	@Autowired
	private SecurityService securityService;

	@Autowired
	private WalletService walletService;

	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	public String getRegister() {
		return "register";
	}

	@RequestMapping(value = "/localLogin", method = RequestMethod.GET)
	public String getLogin() {
		return "login";
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user,
			@RequestParam("confirmPassword") String confirmPassword, ModelMap modelMap) {
		String email = user.getEmail();
		User userWithEmail = userService.getUserByEmail(email);

		if (userWithEmail != null) {
			modelMap.addAttribute("msg",
					"A user already exists with the given email. Please try with different email.");
			return "register";
		}

		String nickName = user.getNickName();
		User userWithNickName = userService.getUserByNickName(nickName);

		if (userWithNickName != null) {
			modelMap.addAttribute("msg", "Nickname :" + nickName
					+ " is already taken and is not available. Please try with a different unique nickname.");
			return "register";
		}

		String password = user.getPassword();

		if (!password.equals(confirmPassword)) {
			modelMap.addAttribute("msg", "Passwords don't match. Please try again.");
			return "register";
		}

		boolean isAlphaNumeric = nickName.matches(ALPHANUMERIC_PATTERN);

		if (!isAlphaNumeric) {
			modelMap.addAttribute("msg",
					"Nickname must contain alphanumeric text only. No character other than alphanumeric is allowed.");
			return "register";
		}

		// Encoding password
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		String token = UUID.randomUUID().toString();
		user.setToken(token);
		user.setProvider(Provider.LOCAL.toString());

		userService.addUser(user);

		try {
			userService.sendEmailForVerification(user);
		} catch (Exception e) {
			modelMap.addAttribute("msg", "Email could not be sent. Please enter valid email address and try again.");
			return "register";
		}

		return "registrationVerification";
	}

	@RequestMapping(value = "/confirmAccount", method = RequestMethod.GET)
	public String confirmAccount(@RequestParam("token") String token, ModelMap modelMap) {
		User user = userService.getByToken(token);

		if (user == null) {
			modelMap.addAttribute("msg", "Invalid verification/confirmation link.");
			return "verificationFailure";
		}

		if (user.isVerified()) {
			modelMap.addAttribute("msg", user.getEmail() + " already verified. Please login to access your account.");
			return "login";
		}

		user.setVerified(true);
		userService.addUser(user);

		walletService.createWallets(user);

		return "registrationSuccess";
	}

	@RequestMapping(value = "/localLogin", method = RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password,
			ModelMap modelMap) {

		if (email.isBlank() || password.isBlank()) {
			modelMap.addAttribute("msg", "Email or Password cannot be empty.");
			return "login";
		}

		// verifying login with security service
		User user = userService.getUserByEmail(email);

		if (user == null) {
			modelMap.addAttribute("msg", "User not found with email" + email);
			return "login";
		} else {
			if (!user.isVerified()) {
				modelMap.addAttribute("msg", "Email address not verified. Please verify email first.");
				return "login";
			}

			if (!user.getProvider().equals(Provider.LOCAL.toString())) {
				modelMap.addAttribute("msg",
						"User " + email + " is not a local user. Please try logging in with Google.");
				return "login";
			}
		}

		boolean isSuccess = securityService.login(email, password);

		if (!isSuccess) {
			modelMap.addAttribute("msg", "Invalid email or password. Please try again.");
			return "login";
		}

		return "displayProfile";
	}

	@RequestMapping(value = "/googleLogin", method = RequestMethod.GET)
	public String googleLogin(ModelMap modelMap) {
		Map<String, Object> attributes = securityService.getCurrentLoggedInUserAttibutesFromOAuth();

		String googleEmail = (String) attributes.get("email");

		User user = userService.getUserByEmail(googleEmail);

		if (user != null) {
			if (!user.getProvider().equals(Provider.GOOGLE.toString())) {
				modelMap.addAttribute("msg",
						"A local user already exists with the same email. Please try local login with the same email.");
				securityService.removeCurrentLoggedInUserFromOAuth();
				return "login";
			}

			if (!user.isVerified()) {
				// modelMap.addAttribute("msg", "Please verify email to login.");
				return "registrationVerification";
			}

			return "profile";
		}

		String firstName = (String) attributes.get("given_name");
		String lastName = (String) attributes.get("family_name");

		User newGoogleUser = new User();

		newGoogleUser.setEmail(googleEmail);
		newGoogleUser.setFirstName(firstName);
		newGoogleUser.setLastName(lastName);
		newGoogleUser.setNickName(googleEmail);
		newGoogleUser.setProvider(Provider.GOOGLE.toString());

		String token = UUID.randomUUID().toString();
		newGoogleUser.setToken(token);

		userService.addUser(newGoogleUser);

		try {
			userService.sendEmailForVerification(newGoogleUser);
		} catch (Exception e) {
			modelMap.addAttribute("msg", "Failed to send email. Please try again.");
			return "verificationFailure";
		}

		return "registrationVerification";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String getProfile() {
		return "profile";

	}

	@RequestMapping(value = "/viewBalance", method = RequestMethod.GET)
	public String viewBalance(ModelMap modelMap) {

		User currentLoggedInUser = securityService.getCurrentLoggedInUser();

		List<Wallet> wallets = walletService.getWallets(currentLoggedInUser);

		modelMap.addAttribute("wallets", wallets);

		return "viewBalance";
	}

	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public String withdraw(@RequestParam("walletId") Long walletId, @RequestParam("symbol") String symbol,
			ModelMap modelMap) {
		modelMap.addAttribute("walletId", walletId);
		modelMap.addAttribute("symbol", symbol);
		return "withdraw";
	}

	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public String deposit(@RequestParam("walletId") Long walletId, @RequestParam("symbol") String symbol,
			ModelMap modelMap) {
		modelMap.addAttribute("walletId", walletId);
		modelMap.addAttribute("symbol", symbol);
		return "deposit";
	}

	@RequestMapping(value = "/updateBalance", method = RequestMethod.POST)
	public String updateBalance(@RequestParam("action") String action, @RequestParam("walletId") Long walletId,
			@RequestParam("symbol") String symbol, @RequestParam("balanceToWithdrawOrDeposit") Double amount,
			ModelMap modelMap) {

		Wallet wallet = walletService.getWallet(walletId, symbol);
		Double balance = wallet.getBalance();

		if (action.equals("withdraw")) {
			if (amount > balance) {
				modelMap.addAttribute("msg", "Maximum withdrawable amount is " + balance);
				return "viewBalance";
			} else {
				balance -= amount;
			}
		} else if (action.equals("deposit")) {
			balance += amount;
		}

		wallet.setBalance(balance);
		walletService.updateWallet(wallet);

		List<Wallet> wallets = walletService.getWallets(walletId);

		modelMap.addAttribute("wallets", wallets);
		modelMap.addAttribute("msg", "Your Wallet balance is updated.");

		return "viewBalance";
	}

}
