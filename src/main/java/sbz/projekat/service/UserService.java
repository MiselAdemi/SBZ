package sbz.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbz.projekat.entity.User;
import sbz.projekat.repository.UserDao;

@Service("UserService")
@Transactional
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public void saveUser(User user)
	{
		userDao.save(user);
	}
	
	public User getUser(String id) {
		return userDao.findById(id);
	}
	
	public List<User> getAll() {
		return userDao.findAll();
		
	}
	
	public void findAndRemove(Query query) {
		userDao.findAndRemove(query);		
	}
	
	public void removeUser(User user) {
		userDao.removeObject(user);
	}

	public User findByUsername(String username) {
		/*Criteria criteria = new Criteria("{{username:"+username+"}}");
		Query q = new Query(criteria);
		User user = userDao.findOne(q);*/
		User user = userDao.findById(username);
		return user; 
	}

}
