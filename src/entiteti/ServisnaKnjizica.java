package entiteti;

import java.util.ArrayList;

public class ServisnaKnjizica {
	private String id;
	private Automobil automobil;
	private ArrayList<ServisAutomobila> listaServisa;
	private boolean obrisan;

	public ServisnaKnjizica() {
		this.id = "";
		this.automobil = new Automobil();
		this.listaServisa = new ArrayList<ServisAutomobila>();
		this.obrisan = false;
	}


	public ServisnaKnjizica(String id, Automobil automobil, ArrayList<ServisAutomobila> listaServisa, boolean obrisan) {
		super();
		this.id = id;
		this.automobil = automobil;
		this.listaServisa = listaServisa;
		this.obrisan = obrisan;
	}


	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Automobil getAutomobil() {
		return automobil;
	}
	
	public String getAutomobilId() {
		return automobil.getId();
	}


	public void setAutomobil(Automobil automobil) {
		this.automobil = automobil;
	}


	public ArrayList<ServisAutomobila> getListaServisa() {
		return listaServisa;
	}


	public void setListaServisa(ArrayList<ServisAutomobila> listaServisa) {
		this.listaServisa = listaServisa;
	}
	public ArrayList<String> getListaServisaId() {
		ArrayList<String> listaServisId = new ArrayList<String>();
		for (ServisAutomobila servis : listaServisa) {
			listaServisId.add(servis.getId());
		}
		return listaServisId;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}


	@Override
	public String toString() {
		return "ServisnaKnjizica [id=" + id + ", automobil=" + automobil + ", listaServisa=" + listaServisa
				+ ", obrisan=" + obrisan + "]";
	}

	
	
	
	
}