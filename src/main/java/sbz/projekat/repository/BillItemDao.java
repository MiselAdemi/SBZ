package sbz.projekat.repository;

import org.springframework.stereotype.Repository;

import sbz.projekat.entity.StavkaRacuna;

@Repository
public class BillItemDao extends MongodbBaseDao<StavkaRacuna> {

	@Override
	protected Class<StavkaRacuna> getEntityClass() {
		return StavkaRacuna.class;
	}
}
