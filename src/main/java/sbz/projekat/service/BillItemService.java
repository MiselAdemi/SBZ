package sbz.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbz.projekat.entity.StavkaRacuna;
import sbz.projekat.repository.BillItemDao;

@Service("BillItemService")
@Transactional
public class BillItemService {

	@Autowired
	private BillItemDao billItemDao;
	
	public void saveBillItem(StavkaRacuna billItem)
	{
		billItemDao.save(billItem);
	}
	
	public StavkaRacuna getBillItem(String id) {
		return billItemDao.findById(id);
	}
	
	public List<StavkaRacuna> getAll() {
		return billItemDao.findAll();
		
	}
	
	public void findAndRemove(Query query) {
		billItemDao.findAndRemove(query);		
	}
	
	public void removeBillItem(StavkaRacuna billItem) {
		billItemDao.removeObject(billItem);
	}

	public StavkaRacuna findById(String id) {
		StavkaRacuna billItem = billItemDao.findById(id);
		return billItem; 
	}
	
}