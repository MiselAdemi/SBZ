package sbz.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbz.projekat.entity.KategorijaKupca;
import sbz.projekat.repository.UserCategoryDao;

@Service("UserCategoryService")
@Transactional
public class UserCategoryService {

	@Autowired
	private UserCategoryDao userCategoryDao;
	
	public void saveUserCategory(KategorijaKupca kategorijaKupca)
	{
		userCategoryDao.save(kategorijaKupca);
	}
	
	public KategorijaKupca getUserCategory(String id) {
		return userCategoryDao.findById(id);
	}
	
	public List<KategorijaKupca> getAll() {
		return userCategoryDao.findAll();
		
	}
	
	public void findAndRemove(Query query) {
		userCategoryDao.findAndRemove(query);		
	}
	
	public void removeUserCategory(KategorijaKupca kategorijaArtikla) {
		userCategoryDao.removeObject(kategorijaArtikla);
	}

	public KategorijaKupca findById(String id) {
		KategorijaKupca kategorijaKupca = userCategoryDao.findById(id);
		return kategorijaKupca; 
	}
	
}