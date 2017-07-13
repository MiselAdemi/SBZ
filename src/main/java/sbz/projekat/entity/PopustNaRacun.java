package sbz.projekat.entity;

import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PopustNaRacun {

	public enum VrstaPopusta {OSNOVNI, DODATNI}
	@Id
	private String sifra;
	@DBRef
	private Racun racun;
	private float procenatUmanjenja;
	private VrstaPopusta vrstaPopusta;
	
	public PopustNaRacun(Racun racun, float procenatUmanjenja, VrstaPopusta vrstaPopusta) {
		super();
		this.sifra = String.valueOf(new Random().nextInt(99999));
		this.racun = racun;
		this.procenatUmanjenja = procenatUmanjenja;
		this.vrstaPopusta = vrstaPopusta;
	}
	public Racun getRacun() {
		return racun;
	}
	public void setRacun(Racun racun) {
		this.racun = racun;
	}
	public float getProcenatUmanjenja() {
		return procenatUmanjenja;
	}
	public void setProcenatUmanjenja(float procenatUmanjenja) {
		this.procenatUmanjenja = procenatUmanjenja;
	}
	public VrstaPopusta getVrstaPopusta() {
		return vrstaPopusta;
	}
	public void setVrstaPopusta(VrstaPopusta vrstaPopusta) {
		this.vrstaPopusta = vrstaPopusta;
	}
	public String getSifra() {
		return sifra;
	}
	
	
}
