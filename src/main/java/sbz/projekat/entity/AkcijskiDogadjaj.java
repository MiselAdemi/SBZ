package sbz.projekat.entity;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AkcijskiDogadjaj {

	@Id
	private String sifra;
	private String naziv;
	private Date pocetak;
	private Date zavrsetak;
	private float popust;
	@DBRef
	private List<KategorijaArtikla> listaKategorija;
	
	/*public AkcijskiDogadjaj(String naziv, Date pocetak, Date zavrsetak, float popust,
			List<KategorijaArtikla> listaKategorija) {
		super();
		this.sifra = String.valueOf(new Random().nextInt(99999));
		this.naziv = naziv;
		this.pocetak = pocetak;
		this.zavrsetak = zavrsetak;
		this.popust = popust;
		this.listaKategorija = listaKategorija;
	}*/
	
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
	public Date getPocetak() {
		return pocetak;
	}
	public void setPocetak(Date pocetak) {
		this.pocetak = pocetak;
	}
	public Date getZavrsetak() {
		return zavrsetak;
	}
	public void setZavrsetak(Date zavrsetak) {
		this.zavrsetak = zavrsetak;
	}
	public float getPopust() {
		return popust;
	}
	public void setPopust(float popust) {
		this.popust = popust;
	}
	public List<KategorijaArtikla> getListaKategorija() {
		return listaKategorija;
	}
	public void setListaKategorija(List<KategorijaArtikla> listaKategorija) {
		this.listaKategorija = listaKategorija;
	}
	
}
