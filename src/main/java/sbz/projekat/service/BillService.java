package sbz.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbz.projekat.entity.Racun;
import sbz.projekat.repository.BillDao;

@Service("BillService")
@Transactional
public class BillService {

	@Autowired
	private BillDao billDao;
	
	public void saveBill(Racun bill)
	{
		billDao.save(bill);
	}
	
	public Racun getRacun(String id) {
		return billDao.findById(id);
	}
	
	public List<Racun> getAll() {
		return billDao.findAll();
		
	}
	
	public void findAndRemove(Query query) {
		billDao.findAndRemove(query);		
	}
	
	public void removeBill(Racun bill) {
		billDao.removeObject(bill);
	}

	public Racun findById(String id) {
		Racun bill = billDao.findById(id);
		return bill; 
	}
	
}