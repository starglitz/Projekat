package entiteti;

import java.util.ArrayList;

import IO.AutomobilIO;
import IO.DeoIO;
import IO.KorisnikIO;
import IO.ServisAutomobilaIO;
import IO.ServisnaKnjizicaIO;
import IO.UcitavanjePodataka;

public class Povezivanje {
	

	public static Musterija getMusterijaPoId(String id) {
		  ArrayList<Musterija> musterije = UcitavanjePodataka.getMusterije();
		    for(Musterija musterija : musterije) {
		      if(id.equals(musterija.getId())) {
		         return musterija;
		      }
		    }
		    return null;
		}
	
	public static Administrator getAdministratorPoId(String id) {
		for(Administrator admin : UcitavanjePodataka.getAdministratori()) {
			if(id.equals(admin.getId())) {
				return admin;
			}
		}
		return null;
	}
	
	public static ArrayList<Deo> getDeoPoId(String[] delovi) {
	

		ArrayList<Deo> deloviObj = UcitavanjePodataka.getDelovi();
		ArrayList<Deo> listaDelova = new ArrayList<Deo>();
		for (int i = 0; i < delovi.length; i++) {
			String idStr = delovi[i];
			for (Deo deo : deloviObj) {
				if(idStr.equals(deo.getId())) {
					listaDelova.add(deo);
			}
			}
	}
		
		
		return listaDelova;
	}
	
	public static Serviser getServiserPoId(String id) {
		  ArrayList<Serviser> serviseri = UcitavanjePodataka.getServiseri();
		    for(Serviser serviser : serviseri) {
		      if(id.equals(serviser.getId())) {
		         return serviser;
		      }
		    }
		    return null;
		}
	
	public static Automobil getAutomobilPoId(String id) {
		ArrayList<Automobil> automobili = UcitavanjePodataka.getAutomobili();
		for(Automobil automobil: automobili) {
			if(id.equals(automobil.getId())) {
				return automobil;
			}
		}
		
		return null;
	}
		
	public static ArrayList<Automobil> getListaAutomobilaPoId(String[] listaIdAutomobila) {
		
	
		ArrayList<Automobil> automobili = UcitavanjePodataka.getAutomobili();
		ArrayList<Automobil> musterijaAutomobili = new ArrayList<Automobil>();
		for (int i = 0; i < listaIdAutomobila.length; i++) {
			String idStr = listaIdAutomobila[i];
			for(Automobil automobil: automobili) {
				if(idStr.equals(automobil.getId())) {
					musterijaAutomobili.add(automobil);
			}
				
			}
		}
		
		return musterijaAutomobili;
}
	
	public static ArrayList<ServisAutomobila> getServisPoId(String[] servisi) {
		
		ArrayList<ServisAutomobila> servisiObj = UcitavanjePodataka.getServisi();
		ArrayList<ServisAutomobila> listaServisa = new ArrayList<ServisAutomobila>();
		for (int i = 0; i < servisi.length; i++) {
			String idStr = servisi[i];
			for (ServisAutomobila servis : servisiObj) {
				if(idStr.equals(servis.getId())) {
					listaServisa.add(servis);
			}
			}
	}
		return listaServisa;
	}
	
	public static ServisnaKnjizica getKnjizicaPoId(String knjizicaId) {
		ArrayList<ServisnaKnjizica> knjizice = UcitavanjePodataka.getKnjizice();
		for(ServisnaKnjizica knjizica : knjizice) {
			if (knjizica.getId() == knjizicaId) {
				return knjizica;
			}
		}
		return null;
	}
	
	public static ArrayList<ServisAutomobila> getServisiPoServiseru(Serviser serviser) {
		ArrayList<ServisAutomobila> servisiServisera = new ArrayList<ServisAutomobila>();
		ArrayList<ServisAutomobila> sviServisi = UcitavanjePodataka.getServisi();
		for(ServisAutomobila servis : sviServisi) {
			if(servis.getServiser() != null) {
			if(servis.getServiserId().equals(serviser.getId())) {
				servisiServisera.add(servis);
			}
			}
		}
		
		return servisiServisera;
	}
	
	public static ServisAutomobila getServisPoId(String id) {
		ArrayList<ServisAutomobila> servisi = UcitavanjePodataka.getServisi();
		for(ServisAutomobila servis : servisi) {
			if(servis.getId().equals(id)) {
				return servis;
			}
		}
		return null;
	}
	
	
	public static ArrayList<Automobil> getAutomobiliMusterije(String musterijaID) {
		ArrayList<Automobil> autoMusterije = new ArrayList<Automobil>();
		for(Automobil auto : UcitavanjePodataka.getAutomobili()) {
			if(auto.getVlasnikId().equals(musterijaID)) {
				autoMusterije.add(auto);
			}
		}
		return autoMusterije;
	}
	
	public static ArrayList<ServisAutomobila> getOdredjeniNeobrServisi(ArrayList<ServisAutomobila> sviOdrServisi) {
		ArrayList<ServisAutomobila> odrNeobrServisi = new ArrayList<ServisAutomobila>();
		for(ServisAutomobila servis : sviOdrServisi) {
			if(servis.isObrisan()==false) {
				odrNeobrServisi.add(servis);
			}
		}
		return odrNeobrServisi;
	}
	
	public static Deo getJedanDeoPoId(String id) {
		for(Deo deo : UcitavanjePodataka.getDelovi()) {
			if(deo.getId().equals(id)) {
				return deo;
			}
		}
		return null;
	}
	public static ArrayList<String> getServisiId() {
		ArrayList<String> servisiId = new ArrayList<String>();
		for(ServisAutomobila servis : UcitavanjePodataka.getServisi()) {
			servisiId.add(servis.getId());
		}
		return servisiId;
	}
	public static ArrayList<String> getDeloviId() {
		ArrayList<String> deloviId = new ArrayList<String>();
		for(Deo deo : UcitavanjePodataka.getDelovi()) {
			deloviId.add(deo.getId());
		}
		return deloviId;
	}
	public static ArrayList<String> getKnjiziceId() {
		ArrayList<String> knjiziceId = new ArrayList<String>();
		for(ServisnaKnjizica knjizica : UcitavanjePodataka.getKnjizice()) {
			knjiziceId.add(knjizica.getId());
		}
		return knjiziceId;
	}
	public static ArrayList<String> getAutomobiliId() {
		ArrayList<String> automobiliId = new ArrayList<String>();
		for(Automobil automobil : UcitavanjePodataka.getAutomobili()) {
			automobiliId.add(automobil.getId());
		}
		return automobiliId;
	}
	public static ArrayList<String> getKorisniciId() {
		ArrayList<String> korisniciId = new ArrayList<String>();
		for(Korisnik korisnik : UcitavanjePodataka.getKorisnici()) {
			korisniciId.add(korisnik.getId());
		}
		return korisniciId;
	}
	
	public static void updateKnjizica(ServisAutomobila servis) {
		for(ServisnaKnjizica knjizica : UcitavanjePodataka.getKnjizice()) {
			if (servis.getAutomobilId().equals(knjizica.getAutomobilId())) {
				knjizica.getListaServisa().add(servis);
			}
		}
		ServisnaKnjizicaIO.ServisnaKnjizicaWriter(UcitavanjePodataka.getKnjizice());
	}

	
	public static ArrayList<ServisAutomobila> getServisiMusterije(ArrayList<Automobil> automobiliMusterije) {
		ArrayList<ServisAutomobila> servisiMusterije = new ArrayList<ServisAutomobila>();
		for(ServisAutomobila servis : UcitavanjePodataka.getNeobrisaniServisi()) {
			for(Automobil auto : automobiliMusterije) {
				if(auto.getId().equals(servis.getAutomobilId())) {
					servisiMusterije.add(servis);
				}
			}
		}
		return servisiMusterije;
	}
	
	public static boolean KnjizicaAutomobilVecPostoji(String AutomobilId) {
		for (ServisnaKnjizica knjizica : UcitavanjePodataka.getKnjizice()) {
			if(knjizica.getAutomobilId().equals(AutomobilId)) {
				return true;
			}
		}
		return false;
	}
}

