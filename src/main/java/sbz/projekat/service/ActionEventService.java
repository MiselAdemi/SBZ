package sbz.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbz.projekat.entity.AkcijskiDogadjaj;
import sbz.projekat.entity.Artikal;
import sbz.projekat.repository.ActionEventDao;

@Service("ActionEventService")
@Transactional
public class ActionEventService {
	
	@Autowired
	private ActionEventDao actionEventDao;
	
	public void saveActionEvent(AkcijskiDogadjaj actionEvent)
	{
		actionEventDao.save(actionEvent);
	}
	
	public AkcijskiDogadjaj getActionEvent(String id) {
		return actionEventDao.findById(id);
	}
	
	public List<AkcijskiDogadjaj> getAll() {
		return actionEventDao.findAll();
		
	}
	
	public void findAndRemove(Query query) {
		actionEventDao.findAndRemove(query);		
	}
	
	public void removeActionEvent(AkcijskiDogadjaj event) {
		actionEventDao.removeObject(event);
	}

	public AkcijskiDogadjaj findById(String id) {
		AkcijskiDogadjaj actionEvent = actionEventDao.findById(id);
		return actionEvent; 
	}
	
}