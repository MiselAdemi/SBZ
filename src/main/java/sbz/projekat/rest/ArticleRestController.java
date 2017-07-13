package sbz.projekat.rest;

import java.util.Date;
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
import sbz.projekat.entity.Artikal;
import sbz.projekat.service.ActionEventService;
import sbz.projekat.service.ArticleService;

@RequestMapping("/api/article")
@RestController
public class ArticleRestController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ActionEventService actionEventService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> get()
	{ 
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rules");
		
		List<Artikal> articles = articleService.getAll();
		
		if (articles == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		for(Artikal a : articles){
			kSession.insert(a);
		}
		
		System.out.println("krecu pravila");
		System.out.println(kSession.fireAllRules());
		
		System.out.println("zavrsila pravila");
		return new ResponseEntity(articles, HttpStatus.OK);
	}
	
	@RequestMapping(value="/allActive", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> getAllActive()
	{ 	
		List<Artikal> articles = articleService.getAllActive();
		
		if (articles == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity(articles, HttpStatus.OK);
	}
	
	@RequestMapping(value="/fillTheStocks/{id}/{count}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> fillTheStocks(@PathVariable("id") String id, @PathVariable("count") int count)
	{ 
		Artikal article = articleService.getArticle(id);
		if(count < (count + article.getBrojnoStanje())) {
			article.setBrojnoStanje(article.getBrojnoStanje() + article.getMinimalnoStanje());
		}else {
			article.setBrojnoStanje(count);
		}
		articleService.saveArticle(article);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> add(@RequestBody Artikal article) { 
		
		article.setSifra(String.valueOf(new Random().nextInt(99999)));
		
		if(article.getBrojnoStanje() > article.getMinimalnoStanje()) {
			article.setDaLiPopunitiZalihe(false);
		}else {
			article.setDaLiPopunitiZalihe(true);
		}
		
		article.setDatumKreiranjaZapisa(new Date());
		
		articleService.saveArticle(article);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/actionEvents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> actionEvents() { 
		List<AkcijskiDogadjaj> list = actionEventService.getAll();
		System.out.println("--------------Events-----------------");
		System.out.println(list);
		System.out.println("--------------Events-----------------");
		
		return new ResponseEntity(list, HttpStatus.OK);
	}
	
}