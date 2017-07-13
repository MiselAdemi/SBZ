package sbz.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbz.projekat.entity.KategorijaArtikla;
import sbz.projekat.repository.ArticleCategoryDao;

@Service("ArticleCategoryService")
@Transactional
public class ArticleCategoryService {
	
	@Autowired
	private ArticleCategoryDao articleCategoryDao;
	
	public void saveArticleCategory(KategorijaArtikla kategorijaArtikla)
	{
		articleCategoryDao.save(kategorijaArtikla);
	}
	
	public KategorijaArtikla getArticleCategory(String id) {
		return articleCategoryDao.findById(id);
	}
	
	public List<KategorijaArtikla> getAll() {
		return articleCategoryDao.findAll();
		
	}
	
	public void findAndRemove(Query query) {
		articleCategoryDao.findAndRemove(query);		
	}
	
	public void removeArticleCategory(KategorijaArtikla kategorijaArtikla) {
		articleCategoryDao.removeObject(kategorijaArtikla);
	}

	public KategorijaArtikla findById(String id) {
		KategorijaArtikla kategorijaArtikla = articleCategoryDao.findById(id);
		return kategorijaArtikla; 
	}
	
}