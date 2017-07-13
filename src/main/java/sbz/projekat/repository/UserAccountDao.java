package sbz.projekat.repository;

import org.springframework.stereotype.Repository;

import sbz.projekat.entity.UserAccount;

@Repository
public class UserAccountDao extends MongodbBaseDao<UserAccount>{

	@Override
	protected Class<UserAccount> getEntityClass() {
		return UserAccount.class;
	}
}
