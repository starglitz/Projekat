package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import entiteti.Administrator;
import entiteti.Automobil;
import entiteti.Deo;
import entiteti.Korisnik;
import entiteti.Musterija;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.Serviser;
import entiteti.ServisnaKnjizica;
import enumeracije.Marka;
import enumeracije.Model;
import enumeracije.Pol;
import enumeracije.Specijalizacija;
import enumeracije.StatusServisa;
import enumeracije.VrstaGoriva;

public class UcitavanjePodataka {

	public static ArrayList<Korisnik> sviKorisnici = KorisnikIO.KorisniciReader();
	public static ArrayList<Automobil> sviAutomobili = AutomobilIO.AutomobilReader();
	public static ArrayList<Deo> sviDelovi = DeoIO.DeloviReader();
	public static ArrayList<ServisAutomobila> sviServisi = ServisAutomobilaIO.ServisAutomobilaReader(); 
	public static ArrayList<ServisnaKnjizica> sveKnjizice = ServisnaKnjizicaIO.ServisnaKnjizicaReader();
		
	public static ArrayList<Deo> getDelovi() {
		return sviDelovi;
	}


	public static void addDeo(Deo deo) {
		getDelovi().add(deo);
	}
	
	
	public static ArrayList<Korisnik> getKorisnici() {
		return sviKorisnici;
	}
	
	public static ArrayList<Musterija> getMusterije() {
		ArrayList<Musterija> musterije = new ArrayList<Musterija>();
		for(Korisnik korisnik : getKorisnici()) {
			if(korisnik instanceof Musterija) {
				Musterija musterija = (Musterija) korisnik;
				musterije.add(musterija);
			}
		}
		
		return musterije;
	}

	public static ArrayList<Serviser> getServiseri() {
		ArrayList<Serviser> serviseri = new ArrayList<Serviser>();
		for(Korisnik korisnik :	getKorisnici()) {
			if(korisnik instanceof Serviser) {
				Serviser serviser = (Serviser) korisnik;
				serviseri.add(serviser);
			}
		}
		return serviseri;
	}
	
	
	public static ArrayList<Administrator> getAdministratori() {
		ArrayList<Administrator> administratori = new ArrayList<Administrator>();
		for(Korisnik korisnik : getKorisnici()) {
			if(korisnik instanceof Administrator) {
				Administrator administrator = (Administrator) korisnik;
				administratori.add(administrator);
			}
		}
		return administratori;
	}
	
	public static ArrayList<Musterija> getNeobrisaneMusterije() {
		ArrayList<Musterija> neobrisaneMusterije = new ArrayList<Musterija>();
		for(Musterija musterija : getMusterije()) {
			if(musterija.isObrisan() == false) {
				 neobrisaneMusterije.add(musterija);
			}
		}
		return neobrisaneMusterije;
	}

	
	public static void changeMusterija(String id, String ime, String prezime, Pol pol, String adresa, String brTelefona,String korisnickoIme, String lozinka, String jmbg, int brBodova, boolean obrisan) {

		for(Korisnik korisnik : getKorisnici()) {
			if(korisnik instanceof Musterija) {
				if(korisnik.getId().equals(id)) {
					Musterija musterija = (Musterija)korisnik;
					musterija.setIme(ime);
					musterija.setPrezime(prezime);
					musterija.setPrezime(prezime);
					musterija.setAdresa(adresa);
					musterija.setBrTelefona(brTelefona);
					musterija.setKorisnickoIme(korisnickoIme);
					musterija.setLozinka(lozinka);
					musterija.setJmbg(jmbg);
					musterija.setBrBodova(brBodova);
					musterija.setObrisan(obrisan);				
		}
			}
			}
		KorisnikIO.KorisniciWriter(sviKorisnici);
	
	}
	
	public static ArrayList<Serviser> getNeobrisaniServiseri() {
		ArrayList<Serviser> neobrisaniServiseri = new ArrayList<Serviser>();
		for(Serviser serviser : getServiseri()) {
			if(serviser.isObrisan() == false) {
				neobrisaniServiseri.add(serviser);
			}
		}
		return neobrisaniServiseri;
	}
		
	
	public static void changeServiser(String id, String ime, String prezime, Pol pol, String adresa, String brTelefona,String korisnickoIme, String lozinka,
			String jmbg, int plata, Specijalizacija specijalizacija, boolean obrisan) {

		for(Korisnik korisnik : getKorisnici()) {
			if(korisnik instanceof Serviser) {
				if(korisnik.getId().equals(id)) {
					Serviser serviser = (Serviser)korisnik;
					serviser.setIme(ime);
					serviser.setPrezime(prezime);
					serviser.setPol(pol);
					serviser.setAdresa(adresa);
					serviser.setBrTelefona(brTelefona);
					serviser.setKorisnickoIme(korisnickoIme);
					serviser.setLozinka(lozinka);
					serviser.setJmbg(jmbg);
					serviser.setPlata(plata);
					serviser.setSpecijalizacija(specijalizacija);
					serviser.setObrisan(obrisan);		
		}
			}
			}
		KorisnikIO.KorisniciWriter(sviKorisnici);
	}
	
	public static ArrayList<Administrator> getNeobrisaniAdministratori() {
		ArrayList<Administrator> neobrisaniAdministratori = new ArrayList<Administrator>();
		for(Administrator administrator : getAdministratori()) {
			if(administrator.isObrisan() == false) { 
				neobrisaniAdministratori.add(administrator);
			}
		}
		return neobrisaniAdministratori;
	}
	
	public static void deleteAdministrator(Administrator administrator) {
		administrator.setObrisan(true);
	}
	
	
	
	public static void changeAdministrator(String id, String ime, String prezime, Pol pol, String adresa, String brTelefona,String korisnickoIme, 
			String lozinka, String jmbg, int plata, boolean obrisan) {
		for(Korisnik korisnik : getKorisnici()) {
			if (korisnik instanceof Administrator) {
				if(korisnik.getId().equals(id)) {
					Administrator admin = (Administrator)korisnik;
					admin.setIme(ime);
					admin.setPrezime(prezime);
					admin.setPol(pol);
					admin.setAdresa(adresa);
					admin.setBrTelefona(brTelefona);
					admin.setKorisnickoIme(korisnickoIme);
					admin.setLozinka(lozinka);
					admin.setJmbg(jmbg);
					admin.setPlata(plata);
					admin.setObrisan(obrisan);			
	}
		}
		}	
		KorisnikIO.KorisniciWriter(sviKorisnici);
		}
	
	public static ArrayList<Korisnik> getNeobrisaniKorisnici() {
		ArrayList<Korisnik> neobrisaniKorisnici = new ArrayList<Korisnik>();
		for(Korisnik korisnik : getKorisnici()) {
			if(korisnik.isObrisan() == false) {
				neobrisaniKorisnici.add(korisnik);
			}
		}
		return neobrisaniKorisnici;
	}
	
	public static void addKorisnik(Korisnik korisnik) {
		if(korisnik instanceof Musterija) {
			getKorisnici().add((Musterija) korisnik);
		}
		else if (korisnik instanceof Serviser) {
			getKorisnici().add((Serviser) korisnik);
		}
		else if (korisnik instanceof Administrator) {
			getKorisnici().add((Administrator) korisnik);
		}
	}
	
	public static void deleteKorisnik(Korisnik korisnik) {
		if(korisnik instanceof Musterija) {
			korisnik.setObrisan(true);
			for(int i = 0; i < getAutomobili().size(); i++) {
				Automobil automobil = getAutomobili().get(i);
				if(automobil.getVlasnikId().equals(korisnik.getId())) {
					deleteAutomobil(automobil);
				}
			}
		}
		else if(korisnik instanceof Serviser) {
			korisnik.setObrisan(true);
			for(int i = 0; i < getServisi().size(); i++) {
			    ServisAutomobila servis = getServisi().get(i);
			    if (servis.getServiserId().equals(korisnik.getId())) {
					deleteServis(servis);
				}
			}
		}
		else if(korisnik instanceof Administrator) {
			korisnik.setObrisan(true);
			}
		}
	
	
	public static ArrayList<Automobil> getNeobrisaniAutomobili() {
		ArrayList<Automobil> neobrisaniAutomobili = new ArrayList<Automobil>();
		for (Automobil automobil: getAutomobili()) {
			if(automobil.isObrisan() == false) {
				neobrisaniAutomobili.add(automobil);
			}
		}
		return neobrisaniAutomobili;
	}
	

	
	public static ArrayList<Automobil> getAutomobili() {
		return sviAutomobili;
	}
	
	public static void addAutomobil(Automobil automobil) {
		getAutomobili().add(automobil);
		int random = (int )(Math.random() * 999999 + 100000);
		ServisnaKnjizica knjizica = new ServisnaKnjizica(Integer.toString(random), automobil, new ArrayList<ServisAutomobila>(), false);
		addKnjizica(knjizica);
		ServisnaKnjizicaIO.ServisnaKnjizicaWriter(getKnjizice());
	}
	
	public static void deleteAutomobil(Automobil automobil) {
		automobil.setObrisan(true);
		for(int i = 0; i < getKnjizice().size(); i++) {
			ServisnaKnjizica knjizica = getKnjizice().get(i);
			if(knjizica.getAutomobilId().equals(automobil.getId())) {
				deleteKnjizica(knjizica);
			}
		}
	}
	
	
	public static void changeAutomobil(String id, Musterija vlasnik, Marka marka, Model model, int godinaProizvodnje, String zapreminaMotora, String snagaMotora, VrstaGoriva vrstaGoriva, boolean obrisan) {
		for(Automobil automobil : getAutomobili()) {
			if(automobil.getId().equals(id)) {
				automobil.setVlasnik(vlasnik);
				automobil.setMarka(marka);
				automobil.setModel(model);
				automobil.setGodinaProizvodnje(godinaProizvodnje);
				automobil.setZapreminaMotora(zapreminaMotora);
				automobil.setSnagaMotora(snagaMotora);
				automobil.setVrstaGoriva(vrstaGoriva);
				automobil.setObrisan(obrisan);
			}
		}
		AutomobilIO.AutomobilWriter(sviAutomobili);
	}
	
	public static ArrayList<ServisAutomobila> getServisi() {
		return sviServisi;
	}
	
	public static ArrayList<ServisAutomobila> getNeobrisaniServisi() {
		ArrayList<ServisAutomobila> neobrisaniServisi = new ArrayList<ServisAutomobila>();
		for(ServisAutomobila servis: getServisi()) {
			if(servis.isObrisan() == false) {
				neobrisaniServisi.add(servis);
			}
		}
		return neobrisaniServisi;
	}
	

	public static void addServis(ServisAutomobila servis) {
		getServisi().add(servis);
		Povezivanje.updateKnjizica(servis);
	}
	
	public static void deleteServis(ServisAutomobila servis) {
		servis.setObrisan(true);
	}
	
	
	public static void changeServis(String id,Automobil automobil, String opis, StatusServisa status, boolean obrisan, Serviser serviser,
			GregorianCalendar termin, ArrayList<Deo> listaDelova, int troskovi) {
		for(ServisAutomobila servis : getServisi()) {
			if(servis.getId().equals(id)) {
				servis.setAutomobil(automobil);
				servis.setServiser(serviser);
				servis.setTermin(termin);
				servis.setOpis(opis);
				servis.setListaDelova(listaDelova);
				servis.setStatus(status);
				servis.setTroskovi(troskovi);
				servis.setObrisan(obrisan);
			}
		}
		ServisAutomobilaIO.ServisAutomobilaWriter(sviServisi);
	}
	
	public static ArrayList<ServisnaKnjizica> getNeobrisaneKnjizice() {
		ArrayList<ServisnaKnjizica> neobrisaneKnjizice = new ArrayList<ServisnaKnjizica>();
		for(ServisnaKnjizica knjizica : getKnjizice()) {
			if(knjizica.isObrisan() == false) {
				neobrisaneKnjizice.add(knjizica);
			}
		}
		return neobrisaneKnjizice;
	}
		
	public static ArrayList<ServisnaKnjizica> getKnjizice() {
		return sveKnjizice;
	}
	
	public static void addKnjizica(ServisnaKnjizica knjizica) {
		getKnjizice().add(knjizica);
	}
	
	public static void deleteKnjizica(ServisnaKnjizica knjizica) {
		knjizica.setObrisan(true);
	}
	
	
	public static void changeKnjizica(String id, Automobil automobil, ArrayList<ServisAutomobila> listaServisa, boolean obrisan) {
		for(ServisnaKnjizica knjizica : getKnjizice()) {
			if(knjizica.getId().equals(id)) {
				knjizica.setAutomobil(automobil);
				knjizica.setListaServisa(listaServisa);
				knjizica.setObrisan(obrisan);
			}
		}
		ServisnaKnjizicaIO.ServisnaKnjizicaWriter(sveKnjizice);
	}
	
	
	public static ArrayList<Deo> getNeobrisaniDelovi () {
		ArrayList<Deo> neobrisaniDelovi = new ArrayList<Deo>();
		for(Deo deo : getDelovi()) {
			if(deo.isObrisan() == false) {
				neobrisaniDelovi.add(deo);
			}
		}
		return neobrisaniDelovi;
	}
	
	
	public static void deleteDeo(Deo deo) {
		deo.setObrisan(true);
	}
	
	public static void changeDeo(String id, Marka marka,Model model,String naziv,int cena, boolean obrisan) {
		for (Deo deo : getDelovi()) {
			if(deo.getId().equals(id)) {
				deo.setMarka(marka);
				deo.setModel(model);
				deo.setNaziv(naziv);
				deo.setCena(cena);
				deo.setObrisan(obrisan);
			}
		}
		DeoIO.DeoWriter(sviDelovi);
	}	
	
	public static Korisnik login(String korisnickoIme, String lozinka) {
		for(Korisnik korisnik : getKorisnici()) {
			if(korisnik.getKorisnickoIme().equalsIgnoreCase(korisnickoIme) && 
			korisnik.getLozinka().equalsIgnoreCase(lozinka) && !korisnik.isObrisan()) {  
				return korisnik;
			}
		}
	return null;
	}
	
}
