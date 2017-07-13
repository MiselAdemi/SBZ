package sbz.projekat.rest;

import java.util.List;
import java.util.Random;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sbz.projekat.entity.AkcijskiDogadjaj;
import sbz.projekat.entity.Racun;
import sbz.projekat.entity.StavkaRacuna;
import sbz.projekat.entity.User;
import sbz.projekat.service.UserAccountService;
import sbz.projekat.service.UserService;

@RequestMapping(value="/api/user")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserAccountService userAccountService;
	
}
