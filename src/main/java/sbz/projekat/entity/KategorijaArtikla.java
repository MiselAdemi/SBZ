package sbz.projekat.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class KategorijaArtikla {

	@Id
	private String sifra;
	private String naziv;
	@DBRef
	private KategorijaArtikla nadkategorija;
	private float maksimalniPopust;
	
	/*public KategorijaArtikla(String sifra, String naziv, KategorijaArtikla nadkategorija, float maksimalniPopust) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.nadkategorija = nadkategorija;
		this.maksimalniPopust = maksimalniPopust;
	}
	*/
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public KategorijaArtikla getNadkategorija() {
		return nadkategorija;
	}
	public void setNadkategorija(KategorijaArtikla nadkategorija) {
		this.nadkategorija = nadkategorija;
	}
	public float getMaksimalniPopust() {
		return maksimalniPopust;
	}
	public void setMaksimalniPopust(float maksimalniPopust) {
		this.maksimalniPopust = maksimalniPopust;
	}
	
}
