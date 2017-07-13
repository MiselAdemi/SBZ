package sbz.projekat.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Artikal {
	
	public enum StatusZapisa {AKTIVAN, ARHIVIRAN}
	
	@Id
	private String sifra;
	private String naziv;
	@DBRef
	private KategorijaArtikla kategorija;
	private float cena;
	private int brojnoStanje;
	private Date datumKreiranjaZapisa;
	private boolean daLiPopunitiZalihe;
	private StatusZapisa statusZapisa;
	private int minimalnoStanje;
	
	/*public Artikal(String sifra, String naziv, KategorijaArtikla kategorija, float cena, int brojnoStanje,
			Date datumKreiranjaZapisa, boolean daLiPopunitiZalihe, StatusZapisa statusZapisa, int minimalnoStanje) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.kategorija = kategorija;
		this.cena = cena;
		this.brojnoStanje = brojnoStanje;
		this.datumKreiranjaZapisa = datumKreiranjaZapisa;
		this.daLiPopunitiZalihe = daLiPopunitiZalihe;
		this.statusZapisa = statusZapisa;
		this.minimalnoStanje = minimalnoStanje;
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
	public KategorijaArtikla getKategorija() {
		return kategorija;
	}
	public void setKategorija(KategorijaArtikla kategorija) {
		this.kategorija = kategorija;
	}
	public float getCena() {
		return cena;
	}
	public void setCena(float cena) {
		this.cena = cena;
	}
	public int getBrojnoStanje() {
		return brojnoStanje;
	}
	public void setBrojnoStanje(int brojnoStanje) {
		this.brojnoStanje = brojnoStanje;
	}
	public Date getDatumKreiranjaZapisa() {
		return datumKreiranjaZapisa;
	}
	public void setDatumKreiranjaZapisa(Date datumKreiranjaZapisa) {
		this.datumKreiranjaZapisa = datumKreiranjaZapisa;
	}
	public boolean isDaLiPopunitiZalihe() {
		return daLiPopunitiZalihe;
	}
	public void setDaLiPopunitiZalihe(boolean daLiPopunitiZalihe) {
		this.daLiPopunitiZalihe = daLiPopunitiZalihe;
	}
	public StatusZapisa getStatusZapisa() {
		return statusZapisa;
	}
	public void setStatusZapisa(StatusZapisa statusZapisa) {
		this.statusZapisa = statusZapisa;
	}
	public int getMinimalnoStanje() {
		return minimalnoStanje;
	}
	public void setMinimalnoStanje(int minimalnoStanje) {
		this.minimalnoStanje = minimalnoStanje;
	}
	
	public boolean isCategory(String catId){
		return helpingFunction(kategorija, catId);
	}
	
	public boolean helpingFunction(KategorijaArtikla category, String catId){
		while(category != null){
			if(category.getSifra().equals(catId)){
				return true;
			}else{
				category = category.getNadkategorija();
			}
		}
		return false;
	}

}
