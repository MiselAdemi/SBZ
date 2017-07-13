package sbz.projekat.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserAccount {
	
	@Id
	private String id;
	private String adresaIsporuke;
	private int nagradniBodovi;
	@DBRef
	private KategorijaKupca kategorija;
	private List<String> shoppingHistory = new ArrayList<>();
	
	/*public UserAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	public UserAccount(String adresaIsporuke, int nagradniBodovi, KategorijaKupca kategorija) {
		super();
		this.adresaIsporuke = adresaIsporuke;
		this.nagradniBodovi = nagradniBodovi;
		this.kategorija = kategorija;
	}*/
	
	public String getAdresaIsporuke() {
		return adresaIsporuke;
	}
	public void setAdresaIsporuke(String adresaIsporuke) {
		this.adresaIsporuke = adresaIsporuke;
	}
	public int getNagradniBodovi() {
		return nagradniBodovi;
	}
	public void setNagradniBodovi(int nagradniBodovi) {
		this.nagradniBodovi = nagradniBodovi;
	}
	public KategorijaKupca getKategorija() {
		return kategorija;
	}
	public void setKategorija(KategorijaKupca kategorija) {
		this.kategorija = kategorija;
	}
	public List<String> getShoppingHistory() {
		return shoppingHistory;
	}
	public void setShoppingHistory(List<String> shoppingHistory) {
		this.shoppingHistory = shoppingHistory;
	}
	public void addShoppingHistoryBill(String bill) {
		this.shoppingHistory.add(bill);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
