package sbz.projekat.repository;

import org.springframework.stereotype.Repository;

import sbz.projekat.entity.Racun;

@Repository
public class BillDao extends MongodbBaseDao<Racun> {

	@Override
	protected Class<Racun> getEntityClass() {
		return Racun.class;
	}
}
