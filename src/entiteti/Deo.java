package entiteti;

import enumeracije.Marka;
import enumeracije.Model;

public class Deo {
	private String id;
	private Marka marka; 
	private Model model; 
	private String naziv;
	private int cena;
	private boolean obrisan;
	
	public Deo() {
		this.id = "";
		this.marka = Marka.AUDI;
		this.model = Model.A4;
		this.naziv = "";
		this.cena = 0;
		this.obrisan = false;
	}
	
	public Deo(String id, Marka marka,Model model,String naziv,int cena, boolean obrisan) {
		this.id = id;
		this.marka = marka;
		this.model = model; 
		this.naziv = naziv;
		this.cena = cena;
		this.obrisan = obrisan;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Marka getMarka() {
		return marka;
	}

	public void setMarka(Marka marka) {
		this.marka = marka;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public int getCena() {
		return cena;
	}

	public void setCena(int cena) {
		this.cena = cena;
	}
	
	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	@Override
	public String toString() {
		return "Deo [id=" + id + ", marka=" + marka + ", model=" + model + ", naziv=" + naziv + ", cena=" + cena
				+ ", obrisan=" + obrisan + "]";
	}

	

	
}
