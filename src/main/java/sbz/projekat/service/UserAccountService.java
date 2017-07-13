package sbz.projekat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sbz.projekat.entity.Artikal;
import sbz.projekat.entity.UserAccount;
import sbz.projekat.repository.UserAccountDao;

@Service("UserAccountService")
@Transactional
public class UserAccountService {
	
	@Autowired
	private UserAccountDao userAccountDao;
	
	public void saveUserAccount(UserAccount userAccount)
	{
		
		System.out.println("---- SAVE -----");
		System.out.println(userAccount.getId());
		System.out.println(userAccount.getShoppingHistory());
		
		userAccountDao.save(userAccount);
		
		System.out.println("---- SAVE -----");
	}
	
	public UserAccount getUserAccount(String id) {
		return userAccountDao.findById(id);
	}
	
	public List<UserAccount> getAll() {
		return userAccountDao.findAll();
		
	}
	
	public void findAndRemove(Query query) {
		userAccountDao.findAndRemove(query);		
	}
	
	public void removeUserAccount(UserAccount userAccount) {
		System.out.println(userAccount.getId());
		userAccountDao.removeObject(userAccount);
	}
	
}
