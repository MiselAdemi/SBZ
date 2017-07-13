package sbz.projekat.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PragPotrosnje {
	
	@Id
	private String id;
	private int donjaGranica;
	private int gornjaGranica;
	private double procenat;

	/*public PragPotrosnje(int donjaGranica, int gornjaGranica, double procenat) {
		super();
		this.donjaGranica = donjaGranica;
		this.gornjaGranica = gornjaGranica;
		this.procenat = procenat;
	}*/

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public int getDonjaGranica() {
		return donjaGranica;
	}

	public void setDonjaGranica(int donjaGranica) {
		this.donjaGranica = donjaGranica;
	}

	public int getGornjaGranica() {
		return gornjaGranica;
	}

	public void setGornjaGranica(int gornjaGranica) {
		this.gornjaGranica = gornjaGranica;
	}

	public double getProcenat() {
		return procenat;
	}

	public void setProcenat(double procenat) {
		this.procenat = procenat;
	}
	
}
