package sbz.projekat.repository;

import org.springframework.stereotype.Repository;

import sbz.projekat.entity.KategorijaKupca;

@Repository
public class UserCategoryDao extends MongodbBaseDao<KategorijaKupca> {

	@Override
	protected Class<KategorijaKupca> getEntityClass() {
		return KategorijaKupca.class;
	}
}
