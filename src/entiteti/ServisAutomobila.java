package entiteti;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import enumeracije.StatusServisa;


public class ServisAutomobila {
	private String id;
	private Automobil automobil;
	private Serviser serviser;
	private GregorianCalendar termin;
	private String opis;
	private ArrayList<Deo> listaDelova; //upotrebljenih u toku servisa 
	private StatusServisa status;
	private int troskovi;
//	private int ukupnaCena;
//	private ServisnaKnjizica knjizica;
	private boolean obrisan;


	public ServisAutomobila() {
		this.id = "";
		this.automobil = new Automobil();
		this.serviser = new Serviser();
		this.termin = new GregorianCalendar();
		this.opis = "";
		this.listaDelova = new ArrayList<Deo>();
		this.status = StatusServisa.ZAKAZAN;	
		this.troskovi = 0;
		this.obrisan = false;
	}
	
	public ServisAutomobila(String id, Automobil automobil, String opis ,StatusServisa status,boolean obrisan) {
		this.id = id;
		this.automobil = automobil;
		this.opis = opis;
		this.status = status;
		this.obrisan = obrisan;
	}


	public ServisAutomobila(String id, Automobil automobil, String opis, StatusServisa status, boolean obrisan, Serviser serviser,
			GregorianCalendar termin, ArrayList<Deo> listaDelova, int troskovi) {
		this.id = id;
		this.automobil = automobil;
		this.opis = opis;
		this.status = status;
		this.obrisan = obrisan;
		this.serviser = serviser;
		this.termin = termin;
		this.listaDelova = listaDelova;
		this.troskovi = troskovi;

		
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


	public Serviser getServiser() {
		return serviser;
	}
	
	public String getServiserId() {
		return serviser.getId();
	}


	public void setServiser(Serviser serviser) {
		this.serviser = serviser;
	}


	public GregorianCalendar getTermin() {
		
		return termin;
	}
	
	public void setTermin(GregorianCalendar termin) {
		this.termin = termin;
	}
	
	
	public String getTerminSimpleDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy.");
		return format.format(termin.getTime());

	}

	
	public  static GregorianCalendar StringToGregorian(String termin) {
		GregorianCalendar datum = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		
		try {
			datum.setTime(format.parse(termin));
			} catch (ParseException e) {
			System.out.println("Neispravan format datuma. Treba da bude u formatu dd.mm.yyyy");
			}
		return datum;
	}

	public String getOpis() {
		return opis;
	}


	public void setOpis(String opis) {
		this.opis = opis;
	}


	public ArrayList<Deo> getListaDelova() {
		return listaDelova;
	}


	public ArrayList<String> getListaIdDelova() {
		ArrayList<String> listaDelovaId = new ArrayList<String>();
		for (Deo deo : listaDelova) {
			listaDelovaId.add(deo.getId());
		}
		return listaDelovaId;
	}
	
	
	public void setListaDelova(ArrayList<Deo> listaDelova) {
		this.listaDelova = listaDelova;
	}


	public StatusServisa getStatus() {
		return status;
	}


	public void setStatus(StatusServisa status) {
		this.status = status;
	}


	public int getTroskovi() {
		return troskovi;
	}
	
	public void setTroskovi(int troskovi) {
		this.troskovi = troskovi;
	}
	
	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}
	

	

	@Override
	public String toString() {
		return "ServisAutomobila [id=" + id + ", automobil=" + automobil + ", serviser=" + serviser + ", termin="
				+ termin + ", opis=" + opis + ", listaDelova=" + listaDelova + ", status=" + status + ", troskovi="
				+ troskovi + ", obrisan=" + obrisan + "]";
	}


	
	
	

	

}