package sbz.projekat.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class KategorijaKupca {

	@Id
	private String sifra;
	private String naziv;
	@DBRef
	private List<PragPotrosnje> pragoviPotrosnje = new ArrayList<>();
	
	/*public KategorijaKupca(String naziv, List<PragPotrosnje> pragoviPotrosnje) {
		super();
		this.naziv = naziv;
		this.pragoviPotrosnje = pragoviPotrosnje;
	}*/
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getSifra() {
		return sifra;
	}
	
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public List<PragPotrosnje> getPragoviPotrosnje() {
		return pragoviPotrosnje;
	}
	public void setPragoviPotrosnje(List<PragPotrosnje> pragoviPotrosnje) {
		this.pragoviPotrosnje = pragoviPotrosnje;
	}
	
}
