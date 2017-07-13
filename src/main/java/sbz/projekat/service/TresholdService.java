package sbz.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbz.projekat.entity.PragPotrosnje;
import sbz.projekat.repository.TresholdDao;

@Service("TresholdService")
@Transactional
public class TresholdService {

	@Autowired
	private TresholdDao tresholdDao;
	
	public void saveTreshold(PragPotrosnje treshold)
	{
		tresholdDao.save(treshold);
	}
	
	public PragPotrosnje getTreshold(String id) {
		return tresholdDao.findById(id);
	}
	
	public List<PragPotrosnje> getAll() {
		return tresholdDao.findAll();
		
	}
	
	public void findAndRemove(Query query) {
		tresholdDao.findAndRemove(query);		
	}
	
	public void removeTreshold(PragPotrosnje treshold) {
		tresholdDao.removeObject(treshold);
	}

	public PragPotrosnje findById(String id) {
		PragPotrosnje treshold = tresholdDao.findById(id);
		return treshold; 
	}
	
}