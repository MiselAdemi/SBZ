package sbz.projekat.repository;

import org.springframework.stereotype.Repository;

import sbz.projekat.entity.PragPotrosnje;

@Repository
public class TresholdDao extends MongodbBaseDao<PragPotrosnje> {

	@Override
	protected Class<PragPotrosnje> getEntityClass() {
		return PragPotrosnje.class;
	}
}
