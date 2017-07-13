package sbz.projekat.rest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import sbz.projekat.entity.KategorijaKupca;
import sbz.projekat.entity.User;
import sbz.projekat.entity.User.Uloga;
import sbz.projekat.entity.UserAccount;
import sbz.projekat.service.UserAccountService;
import sbz.projekat.service.UserCategoryService;
import sbz.projekat.service.UserService;

@Controller
@RequestMapping(value = "/api/authentication")
public class AuthenticationController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserCategoryService ucs;

	@RequestMapping(value="/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody User user) throws Exception {
		String username = user.getUsername();
		String password = user.getPassword();
		System.out.println(username);
		System.out.println(password);
		// Authenticate the user using the credentials provided
		User u = authenticate(username, password);
		Uloga uloga = u.getRole();
		if (uloga != null) {

			// Issue a token for the user

			// Return the token on the response
			return new ResponseEntity(u, HttpStatus.OK);
		} else
			System.out.println("NEUSPELO");
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);

	}

	private User authenticate(String username, String password)
			throws Exception {
		// Authenticate against a database, LDAP, file or whatever
		// Throw an Exception if the credentials are invalid
		System.out.println(username);
		User u = userService.findByUsername(username);
		if (u.getPassword().equals(password)) {
			return u;
		} else {
			return null;
		}
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody User user, UriComponentsBuilder uriBuilder){
		System.out.println("Registration started...");
		
		User possibleUser =  userService.findByUsername(user.getUsername());
		user.setRole(User.Uloga.CUSTOMER);
			
		if(possibleUser == null) {
			if(user.getRole() == User.Uloga.CUSTOMER) {
				UserAccount account = new UserAccount();
				
				for(KategorijaKupca kk : ucs.getAll()) {
					if(kk.getNaziv().equals("regular"))
						account.setKategorija(kk);
				}
				
				account.setNagradniBodovi(0);
				
				userAccountService.saveUserAccount(account);
				user.setUserAccount(account);
				user.setRegistration_date(new Date());
			}
		
			userService.saveUser(user);
			return new ResponseEntity(user, HttpStatus.CREATED);
		}else {
			return new ResponseEntity(HttpStatus.ACCEPTED);
		}
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public String test(){
		System.out.println("TEST");

		return "Test controller";
	}

}
