package sbz.projekat.repository;

import org.springframework.stereotype.Repository;

import sbz.projekat.entity.User;

@Repository
public class UserDao extends MongodbBaseDao<User>{

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
}
