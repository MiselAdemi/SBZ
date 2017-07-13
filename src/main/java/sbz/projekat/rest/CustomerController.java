package sbz.projekat.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sbz.projekat.entity.AkcijskiDogadjaj;
import sbz.projekat.entity.Artikal;
import sbz.projekat.entity.PopustNaStavku;
import sbz.projekat.entity.Racun;
import sbz.projekat.entity.StavkaRacuna;
import sbz.projekat.entity.User;
import sbz.projekat.entity.UserAccount;
import sbz.projekat.entity.Racun.StanjeRacuna;
import sbz.projekat.service.ActionEventService;
import sbz.projekat.service.ArticleService;
import sbz.projekat.service.BillItemService;
import sbz.projekat.service.BillService;
import sbz.projekat.service.UserAccountService;
import sbz.projekat.service.UserService;

@RequestMapping("/api/customer")
@RestController
public class CustomerController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ActionEventService actionEventService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private BillItemService billItemService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/createBill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> createBill(@RequestBody Racun bill) { 
		Random random = new Random();
		bill.setSifra(String.valueOf(random.nextInt(99999)));
		bill.setStanje(Racun.StanjeRacuna.PORUCENO);
		
		for(StavkaRacuna sr : bill.getListaStavki()) {
			sr.setRacun(bill);
		}
		
		// Prepare and fire rules
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rules");
		
		kSession.insert(bill);
		
		for(StavkaRacuna sr : bill.getListaStavki()) {
			kSession.insert(sr);
		}
		
		List<AkcijskiDogadjaj> actionEvents = actionEventService.getAll();
		
		for(AkcijskiDogadjaj ae : actionEvents) {
			kSession.insert(ae);
		}
		
		System.out.println("krecu pravila");
		System.out.println(kSession.fireAllRules());
		
		System.out.println("zavrsila pravila");
		
		float finalPrice = 0;
		
		for(StavkaRacuna sr : bill.getListaStavki()){
			finalPrice += sr.getKonacnaCenaStavke();
		}
		
		System.out.println("Konacna cena " + finalPrice);
		UserAccount tmpAccount = userAccountService.getUserAccount(bill.getKupac().getUserAccount().getId());
		System.out.println(tmpAccount.getNagradniBodovi());
		bill.getKupac().getUserAccount().setNagradniBodovi(tmpAccount.getNagradniBodovi());
		
		bill.setKonacnaCena((finalPrice * (100 - bill.getProcenatUmanjenja())) / 100);
		
		Racun r = new Racun();
		r.setOriginalnaCena(bill.getOriginalnaCena());
		r.setProcenatUmanjenja(bill.getProcenatUmanjenja());
		r.setKonacnaCena(bill.getKonacnaCena());
		r.setKupac(bill.getKupac());

		for(StavkaRacuna sr : bill.getListaStavki()){
			StavkaRacuna stavka = sr;
			stavka.setRacun(null);
			stavka.setListaPopusta(null);
			
			r.getListaStavki().add(stavka);
		}
		
		return new ResponseEntity(r, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/buy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> buy(@RequestBody Racun bill) { 

		User u = userService.findByUsername(bill.getKupac().getUsername());
		bill.setKupac(u);
		bill.setKonacnaCena(bill.getKonacnaCena() - bill.getBrojPotrosenihBodova());
		bill.setStanje(Racun.StanjeRacuna.PORUCENO);
		System.out.println("-------------Racun---------------");
		System.out.println(bill.getBrojPotrosenihBodova());
		
		for(StavkaRacuna sr : bill.getListaStavki()) {
			billItemService.saveBillItem(sr);
		}
		
		billService.saveBill(bill);
		
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession =  kContainer.newKieSession("ksession-rules-final");

		kSession.insert(bill);
		kSession.insert(bill.getKupac());

		System.out.println("krecu pravila");
		System.out.println(kSession.fireAllRules());

		System.out.println("zavrsila pravila");

		billService.saveBill(bill);
		
		UserAccount account = userAccountService.getUserAccount(bill.getKupac().getUserAccount().getId());

		List<String> list = new ArrayList<>();
		for(String key : account.getShoppingHistory()) {
			list.add(key);
		}
		
		list.add(billService.getRacun(bill.getSifra()).getSifra());
		
		account.setShoppingHistory(list);

		System.out.println(account.getShoppingHistory().size());		
		userAccountService.saveUserAccount(account);

		return new ResponseEntity(bill, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/refresh", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> cutomerRefresh(@RequestBody User user) { 
		
		User u = userService.findByUsername(user.getUsername());
		System.out.println(u.getUsername());
		System.out.println(u.getUserAccount().getShoppingHistory().size());
		
		return new ResponseEntity(u, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/shoppingHistory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getShoppingHistory(@RequestBody User user) { 
		
		User u = userService.findByUsername(user.getUsername());
		
		UserAccount account = userAccountService.getUserAccount(u.getUserAccount().getId());

		List<Racun> list = new ArrayList<>();
		for(String key : account.getShoppingHistory()) {
			list.add(billService.getRacun(key));
		}
		
		return new ResponseEntity(list, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/allBills", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getAllBills()
	{ 
		List<Racun> list = billService.getAll();

		return new ResponseEntity(list, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/approveBill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> approveBill(@RequestBody Racun bill) { 
		Racun r = billService.getRacun(bill.getSifra());
		
		for(StavkaRacuna sr : r.getListaStavki()) {
			Artikal article = articleService.getArticle(sr.getArtikal().getSifra());
			if(article.getBrojnoStanje() < sr.getKolicina()) {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}
		}
		
		r.setStanje(StanjeRacuna.REALIZOVANO);
		
		for(StavkaRacuna sr : r.getListaStavki()){
			Artikal a = articleService.getArticle(sr.getArtikal().getSifra());
			a.setBrojnoStanje(a.getBrojnoStanje() - sr.getKolicina());
			articleService.saveArticle(a);
		}
		
		billService.saveBill(r);
		
		User u = userService.getUser(r.getKupac().getUsername());
		UserAccount account = userAccountService.getUserAccount(u.getUserAccount().getId());
		
		account.setNagradniBodovi(account.getNagradniBodovi() - r.getBrojPotrosenihBodova() + r.getBrojOstvarenihBodova());
		userAccountService.saveUserAccount(account);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/removeBill", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> removeBill(@RequestBody Racun bill) { 
		Racun r = billService.getRacun(bill.getSifra());
		r.setStanje(StanjeRacuna.OTKAZANO);
		
		billService.saveBill(r);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/updateUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> removeBill(@RequestBody User user) { 
		User u = userService.getUser(user.getUsername());
		u.setFirst_name(user.getFirst_name());
		u.setLast_name(user.getLast_name());
		
		userService.saveUser(u);
		
		return new ResponseEntity(u, HttpStatus.OK);
	}
	
	private void prepareAndFireRules(Racun bill) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession =  kContainer.newKieSession("ksession-rules");
		kSession.insert(bill);
		
		for(StavkaRacuna sr : bill.getListaStavki()) {
			kSession.insert(sr);
		}
		
		System.out.println("krecu pravila");
		System.out.println(kSession.fireAllRules());
		
		System.out.println("zavrsila pravila");
	}

}
