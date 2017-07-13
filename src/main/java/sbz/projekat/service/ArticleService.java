package sbz.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbz.projekat.entity.Artikal;
import sbz.projekat.repository.ArticleDao;

@Service("ArticleService")
@Transactional
public class ArticleService {
	
	@Autowired
	private ArticleDao articleDao;
	
	public void saveArticle(Artikal artikal)
	{
		articleDao.save(artikal);
	}
	
	public Artikal getArticle(String id) {
		return articleDao.findById(id);
	}
	
	public List<Artikal> getAll() {
		return articleDao.findAll();
		
	}
	
	public void findAndRemove(Query query) {
		articleDao.findAndRemove(query);		
	}
	
	public void removeArticle(Artikal artikal) {
		articleDao.removeObject(artikal);
	}

	public Artikal findById(String id) {
		Artikal artikal = articleDao.findById(id);
		return artikal; 
	}
	
	public List<Artikal> getAllActive() {
		Query query = new Query();
		query.addCriteria(Criteria.where("statusZapisa").is("AKTIVAN"));
		List<Artikal> articles = articleDao.find(query);
		
		return articles;
	}
}