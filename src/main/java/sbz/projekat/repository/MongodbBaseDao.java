package sbz.projekat.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;


public abstract class MongodbBaseDao<T> {
	
	@Autowired
	@Qualifier("mongoTemplate")
	protected MongoTemplate mongoTemplate;


	
	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	
	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}

	public List<T> findAll() {
		return this.mongoTemplate.findAll(getEntityClass());
	}

	public T findAndModify(Query query, Update update) {

		return this.mongoTemplate.findAndModify(query, update, this.getEntityClass());
	}

	public T findAndRemove(Query query) {
		return this.mongoTemplate.findAndRemove(query, this.getEntityClass());
	}


	public void updateFirst(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, this.getEntityClass());
	}

	public T save(T entity) {
		mongoTemplate.save(entity);
		return entity;
	}

	public T findById(String id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	
	public T findById(String id, String collectionName) {
		return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
	}

	protected abstract Class<T> getEntityClass();

//	protected abstract void setMongoTemplate(MongoTemplate mongoTemplate);

	public void removeObject(Object object) {
		mongoTemplate.remove(object);
	}

}
