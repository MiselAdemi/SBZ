package sbz.projekat.testBaze;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sbz.projekat.entity.Artikal;
import sbz.projekat.entity.KategorijaArtikla;
import sbz.projekat.entity.KategorijaKupca;
import sbz.projekat.entity.PragPotrosnje;
import sbz.projekat.entity.Racun;
import sbz.projekat.entity.StavkaRacuna;
import sbz.projekat.entity.User;
import sbz.projekat.entity.User.Uloga;
import sbz.projekat.entity.UserAccount;
import sbz.projekat.service.ActionEventService;
import sbz.projekat.service.ArticleCategoryService;
import sbz.projekat.service.ArticleService;
import sbz.projekat.service.BillItemService;
import sbz.projekat.service.BillService;
import sbz.projekat.service.TresholdService;
import sbz.projekat.service.UserAccountService;
import sbz.projekat.service.UserCategoryService;
import sbz.projekat.service.UserService;

@Controller
@RequestMapping(value="/api/radSaUserom")
public class RadSaUserom {
	
	@Autowired
	private UserService us;
	
	@Autowired
	private ArticleService as;
	
	@Autowired
	private ArticleCategoryService acs;
	
	@Autowired
	private ActionEventService aes;
	
	@Autowired
	private UserAccountService uas;
	
	@Autowired
	private TresholdService ts;
	
	@Autowired
	private UserCategoryService ucs;
	
	@Autowired
	private BillService bs;
	
	@Autowired
	private BillItemService bis;
	
	@RequestMapping(value="/add", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(){
		
		System.out.println("USAO");
	
		Uloga kupac = Uloga.CUSTOMER;
		Uloga menadzer = Uloga.MANAGER;
		Uloga prodavac = Uloga.EMPLOYEE;
		
		//KategorijaKupca kk1 = new KategorijaKupca("gold", new ArrayList<>());
		
		//ucs.saveUserCategory(kk1);
		
		//UserAccount ua1 = new UserAccount("Adresa 1", 0, kk1, new ArrayList<>());
		
		//uas.saveUserAccount(ua1);

		User u3 = new User("User3","ime3","prezime3","sifra", menadzer, null, null);
		User u4 = new User("User2","ime4","prezime4","sifra", prodavac, null, null);

		us.saveUser(u3);
		us.saveUser(u4);
		
		HttpHeaders headers = new HttpHeaders();
		
		return new ResponseEntity(headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> get()
	{ 
		
		/*KategorijaArtikla k1 = new KategorijaArtikla("1", "Kat 1", null, 10);
		KategorijaArtikla k2 = new KategorijaArtikla("2", "Kat 2", null, 30);
		KategorijaArtikla k3 = new KategorijaArtikla("4", "Kat 4", k2, 50);
		
		acs.saveArticleCategory(k1);
		acs.saveArticleCategory(k2);
		acs.saveArticleCategory(k3);
		
		Artikal a1 = new Artikal("1", "Artikal 1", k1, 100, 30, new Date(), false, Artikal.StatusZapisa.AKTIVAN, 2);
		Artikal a2 = new Artikal("2", "Artikal 2", k2, 200, 10, new Date(), false, Artikal.StatusZapisa.AKTIVAN, 4);
		Artikal a3 = new Artikal("3", "Artikal 3", k3, 300, 10, new Date(), false, Artikal.StatusZapisa.AKTIVAN, 8);
		Artikal a4 = new Artikal("4", "Artikal 4", k2, 300, 2, new Date(), false, Artikal.StatusZapisa.ARHIVIRAN, 4);
		
		as.saveArticle(a1);
		as.saveArticle(a2);
		as.saveArticle(a3);
		as.saveArticle(a4);
		
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		
		List<KategorijaArtikla> l1 = new ArrayList<>();
		l1.add(k1);
		
		List<KategorijaArtikla> l2 = new ArrayList<>();
		l1.add(k2);
		
		AkcijskiDogadjaj ae1 = new AkcijskiDogadjaj("Leto", new Date(), dt, 10, l1);
		AkcijskiDogadjaj ae2 = new AkcijskiDogadjaj("Zima", new Date(), dt, 7, l2);
		
		aes.saveActionEvent(ae1);
		aes.saveActionEvent(ae2);*/
		
		List<User> users = us.getAll();
		for(int i=0; i<users.size();i++) {
			System.out.println("-------------------------------------");
			System.out.println(users.get(i).getUsername());
		}
		if (users == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(users, HttpStatus.OK);
	}
	
	@RequestMapping(value="/allArticles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getArticles()
	{ 
		
		List<Artikal> articles = as.getAll();
		for(int i=0; i<articles.size();i++) {
			System.out.println("-------------------------------------");
			System.out.println(articles.get(i).getNaziv());
		}
		if (articles == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(articles, HttpStatus.OK);
	}
	
	@RequestMapping(value="/allAccounts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getAccounts()
	{ 
		
		List<UserAccount> accounts = uas.getAll();
		for(int i=0; i<accounts.size();i++) {
			System.out.println("-------------------------------------");
			System.out.println(accounts.get(i).getId());
		}
		if (accounts == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(accounts, HttpStatus.OK);
	}
	
	@RequestMapping(value="/allTresh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getTresh()
	{ 
		
		List<PragPotrosnje> treshs = ts.getAll();
		for(int i=0; i<treshs.size();i++) {
			System.out.println("-------------------------------------");
			System.out.println(treshs.get(i).getId());
		}
		if (treshs == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(treshs, HttpStatus.OK);
	}
	
	@RequestMapping(value="/allUserCat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getUserCat()
	{ 
		
		List<KategorijaKupca> cats = ucs.getAll();
		for(int i=0; i<cats.size();i++) {
			System.out.println("-------------------------------------");
			System.out.println(cats.get(i).getSifra());
		}
		if (cats == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(cats, HttpStatus.OK);
	}
	
	@RequestMapping(value="/allBills", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getBills()
	{ 
		
		List<Racun> bills = bs.getAll();
		for(int i=0; i<bills.size();i++) {
			System.out.println("-------------------------------------");
			System.out.println(bills.get(i).getSifra());
		}
		if (bills == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(bills, HttpStatus.OK);
	}
	
	@RequestMapping(value="/allBillItems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getBillItems()
	{ 
		
		List<StavkaRacuna> items = bis.getAll();
		for(int i=0; i<items.size();i++) {
			System.out.println("-------------------------------------");
			System.out.println(items.get(i).getId());
		}
		if (items == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(items, HttpStatus.OK);
	}
	
	@RequestMapping(value="/removeAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> removeAll()
	{ 
		List<User> users = us.getAll();
		if (users == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		while (users.size()>0)
		{
			int index = users.size() - 1;
			us.removeUser(users.get(index));
			users.remove(index);
			System.out.println("OSTALO JOS: "+users.size());
		}
		return new ResponseEntity(users, HttpStatus.OK);
	
	}
	
	@RequestMapping(value="/removeAllArticles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> removeAllArticles()
	{ 
		List<Artikal> artikals = as.getAll();
		if (artikals == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		while (artikals.size()>0)
		{
			int index = artikals.size() - 1;
			as.removeArticle(artikals.get(index));
			artikals.remove(index);
			System.out.println("OSTALO JOS: "+artikals.size());
		}
		return new ResponseEntity(artikals, HttpStatus.OK);
	
	}
	
	@RequestMapping(value="/removeAllArticlesCat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> removeAllArticlesCat()
	{ 
		List<KategorijaArtikla> artikals = acs.getAll();
		if (artikals == null) {
		
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		while (artikals.size()>0)
		{
			int index = artikals.size() - 1;
			acs.removeArticleCategory(artikals.get(index));
			artikals.remove(index);
			System.out.println("OSTALO JOS: "+artikals.size());
		}
		return new ResponseEntity(artikals, HttpStatus.OK);
	
	}
	
	@RequestMapping(value="/pretraga", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> pretraga()
	{ 
		System.out.println("USAO");
		User user = null;
		
		user = us.findByUsername("korisnik1");
		System.out.println("PRONADJEN KORISNIK: " + user.getUsername());
		
		return new ResponseEntity(user, HttpStatus.OK);
	
	}
	
	

}
