package sbz.projekat.repository;

import org.springframework.stereotype.Repository;

import sbz.projekat.entity.AkcijskiDogadjaj;

@Repository
public class ActionEventDao extends MongodbBaseDao<AkcijskiDogadjaj> {

	@Override
	protected Class<AkcijskiDogadjaj> getEntityClass() {
		return AkcijskiDogadjaj.class;
	}

}
