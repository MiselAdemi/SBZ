package sbz.projekat.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sbz.projekat.entity.KategorijaKupca;
import sbz.projekat.entity.PragPotrosnje;
import sbz.projekat.entity.User;
import sbz.projekat.entity.UserAccount;
import sbz.projekat.service.TresholdService;
import sbz.projekat.service.UserAccountService;
import sbz.projekat.service.UserCategoryService;
import sbz.projekat.service.UserService;

@RequestMapping("/api/userCategory")
@RestController
public class UserCategoryController {

	@Autowired
	private UserCategoryService userCategoryService;
	
	@Autowired
	private TresholdService treshService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserService userService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> get()
	{ 
		List<KategorijaKupca> userCategories = userCategoryService.getAll();
		
		if (userCategories == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(userCategories, HttpStatus.OK);
	}
	
	@RequestMapping(value="/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> addCategory(@RequestBody KategorijaKupca category) { 
		for(PragPotrosnje pp : category.getPragoviPotrosnje()) {
			if(pp.getId() == "") {
				pp.setId(String.valueOf(new Random().nextInt(99999)));
			}
		}
		userCategoryService.saveUserCategory(category);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> deleteCategory(@RequestBody KategorijaKupca category) { 
		userCategoryService.removeUserCategory(category);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateCategory(@RequestBody KategorijaKupca category) { 
		
		List<PragPotrosnje> ppList = new ArrayList<PragPotrosnje>();
		
		for(PragPotrosnje pp : category.getPragoviPotrosnje()) {
			if(pp.getId() == "") {
				pp.setId(String.valueOf(new Random().nextInt(99999)));
				
				treshService.saveTreshold(pp);
			}
			
			ppList.add(pp);
		}
		
		KategorijaKupca kk = userCategoryService.getUserCategory(category.getSifra());
		kk.setPragoviPotrosnje(ppList);
		
		userCategoryService.saveUserCategory(kk);
		
		for(UserAccount ua : userAccountService.getAll()) {
			if(ua.getKategorija() != null) {
				if(ua.getKategorija().getSifra().equals(kk.getSifra())) {
					ua.setKategorija(kk);
					userAccountService.saveUserAccount(ua);
					
					for(User u : userService.getAll()) {
						if(u.getUserAccount() != null) {
							if(u.getUserAccount().getId().equals(ua.getId())) {
								u.setUserAccount(ua);
								userService.saveUser(u);
							}
						}
					}
				}
			}
		}
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
}
