package sbz.projekat.repository;

import org.springframework.stereotype.Repository;

import sbz.projekat.entity.KategorijaArtikla;

@Repository
public class ArticleCategoryDao extends MongodbBaseDao<KategorijaArtikla>  {

	@Override
	protected Class<KategorijaArtikla> getEntityClass() {
		return KategorijaArtikla.class;
	}
}
