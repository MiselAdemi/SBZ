package sbz.projekat.repository;

import org.springframework.stereotype.Repository;

import sbz.projekat.entity.Artikal;

@Repository
public class ArticleDao extends MongodbBaseDao<Artikal> {

	@Override
	protected Class<Artikal> getEntityClass() {
		return Artikal.class;
	}

}
