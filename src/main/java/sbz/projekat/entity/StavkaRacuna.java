package sbz.projekat.entity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSourceType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import sbz.projekat.entity.PopustNaRacun.VrstaPopusta;

@Document
public class StavkaRacuna {

	@Id
	private String id;
	//@Indexed(unique = true)
	private int redniBroj;
	@DBRef
	private Artikal artikal;
	private String artikalSifra;
	private float cena;
	private int kolicina;
	private float originalnaUkupnaCenaStavke;
	private float procenatUmanjenja;
	private float konacnaCenaStavke;
	@DBRef
	private Racun racun;
	@DBRef
	private List<PopustNaStavku> listaPopusta = new ArrayList<>();
	
	public int getRedniBroj() {
		return redniBroj;
	}
	public void setRedniBroj(int redniBroj) {
		this.redniBroj = redniBroj;
	}
	public Artikal getArtikal() {
		return artikal;
	}
	public void setArtikal(Artikal artikal) {
		this.artikal = artikal;
	}
	public float getCena() {
		return cena;
	}
	public void setCena(float cena) {
		this.cena = cena;
	}
	public int getKolicina() {
		return kolicina;
	}
	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	public float getOriginalnaUkupnaCenaStavke() {
		return originalnaUkupnaCenaStavke;
	}
	public void setOriginalnaUkupnaCenaStavke(float originalnaUkupnaCenaStavke) {
		this.originalnaUkupnaCenaStavke = originalnaUkupnaCenaStavke;
	}
	public float getProcenatUmanjenja() {
		return procenatUmanjenja;
	}
	public void setProcenatUmanjenja(float procenatUmanjenja) {
		if(procenatUmanjenja == 0) {
			float osnovni = this.bestDiscount();
			if(osnovni != 0) {
				procenatUmanjenja = osnovni;
			}
		}

		if(procenatUmanjenja > artikal.getKategorija().getMaksimalniPopust()) {
			this.procenatUmanjenja = artikal.getKategorija().getMaksimalniPopust();
		}else {
			this.procenatUmanjenja = procenatUmanjenja;
		}
	}
	public float getKonacnaCenaStavke() {
		return konacnaCenaStavke;
	}
	public void setKonacnaCenaStavke(float konacnaCenaStavke) {
		this.konacnaCenaStavke = konacnaCenaStavke;
	}
	public List<PopustNaStavku> getListaPopusta() {
		return listaPopusta;
	}
	public void setListaPopusta(List<PopustNaStavku> listaPopusta) {
		this.listaPopusta = listaPopusta;
	}
	public String getId() {
		return id;
	}
	public String getArtikalSifra() {
		return artikalSifra;
	}
	public void setArtikalSifra(String artikalSifra) {
		this.artikalSifra = artikalSifra;
	}
	public Racun getRacun() {
		return racun;
	}
	public void setRacun(Racun racun) {
		this.racun = racun;
	}
	
	public boolean lastNDays(int day){
		Date now = new Date();
		
		final String uri = "http://localhost:8080/SBZProjekat/api/customer/allBills";

		RestTemplate rt = new RestTemplate();
		String result = rt.getForObject(uri, String.class);
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
	        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
	           return new Date(jsonElement.getAsJsonPrimitive().getAsLong()); 
	        }
	     })
	     .create();
		
		Racun[] bills = gson.fromJson(result, Racun[].class);
		
		final String uri1 = "http://localhost:8080/SBZProjekat/api/customer/shoppingHistory";
		rt = new RestTemplate();

		User any = racun.getKupac();
		String resultHistory = rt.postForObject(uri1, any, String.class);
		Racun[] shoppingHistoryList = gson.fromJson(resultHistory, Racun[].class);
		
		List<Racun> userBills = new ArrayList<>();
		for(Racun shr : shoppingHistoryList) {
			for(Racun r : bills) {
				if(shr.getSifra().equals(r.getSifra())) {
					userBills.add(r);
				}
			}
		}
		
		for(Racun bill : userBills){
			for(StavkaRacuna sr : bill.getListaStavki()){
				if(sr.getArtikalSifra() != null) {
					if(sr.getArtikalSifra().equals(artikal.getSifra())){
						if((System.currentTimeMillis() - bill.getDatum().getTime()) / (24 * 60 * 60 * 1000d) < day){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public float bestDiscount(){
		if(firstOsnovniId() != -1){
			float biggest = listaPopusta.get(firstOsnovniId()).getProcenatUmanjenja();
			
			for(PopustNaStavku popustNaStavku : listaPopusta){
				if(popustNaStavku.getProcenatUmanjenja() > biggest && popustNaStavku.getVrstaPopusta().equals(VrstaPopusta.OSNOVNI))
					biggest = popustNaStavku.getProcenatUmanjenja();
			}
			return biggest;
		}else {
			return 0;
		}
	}
	
	public int firstOsnovniId(){
		for(PopustNaStavku popustNaStavku : listaPopusta){
			if(popustNaStavku.getVrstaPopusta().toString().equals(VrstaPopusta.OSNOVNI.toString())){
				return listaPopusta.indexOf(popustNaStavku);
			}
		}
		return -1;
	}
	
}
