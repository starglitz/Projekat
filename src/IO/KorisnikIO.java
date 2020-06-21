package IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.Administrator;
import entiteti.Automobil;
import entiteti.Korisnik;
import entiteti.Musterija;
import entiteti.Povezivanje;
import entiteti.Serviser;
import enumeracije.Marka;
import enumeracije.Pol;
import enumeracije.Specijalizacija;
import entiteti.Administrator;

public class KorisnikIO {

	
	public static ArrayList<Korisnik> KorisniciReader() {

		ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
		
		try {
			File file = new File("src/podaci/korisnici.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] osobine = line.split("\\|");
				String id = osobine[0];
				String ime = osobine[2];
				String prezime = osobine[3];
				Pol pol = Pol.valueOf(osobine[4]);
				String adresa = osobine[5];
				String brojTelefona = osobine[6];
				String korisnickoIme = osobine[7];
				String lozinka = osobine[8];
				String uloga = osobine[1];
				String jmbg = osobine[9];
	
				if (uloga.equals("administrator") == true) {
					int plata = Integer.parseInt(osobine[10]);
					boolean obrisan = Boolean.parseBoolean(osobine[11]);
					Administrator administrator = new Administrator(id, ime, prezime, pol, adresa, brojTelefona, korisnickoIme, lozinka, jmbg, plata, obrisan);
					korisnici.add(administrator);
				}
				else if(uloga.equals("serviser") == true) {
					int plata = Integer.parseInt(osobine[10]);
					Specijalizacija specijalizacija = Specijalizacija.valueOf(osobine[11]);
					boolean obrisan = Boolean.parseBoolean(osobine[12]);
					Serviser serviser = new Serviser(id, ime, prezime, pol, adresa, brojTelefona, korisnickoIme, lozinka, jmbg, plata, specijalizacija, obrisan);
					korisnici.add(serviser);
				}
				else if(uloga.equals("musterija") == true) {
					int brojBodova = Integer.parseInt(osobine[10]);
					boolean obrisan = Boolean.parseBoolean(osobine[11]);
					Musterija musterija = new Musterija(id, ime, prezime, pol, adresa, brojTelefona, korisnickoIme, lozinka, jmbg, brojBodova, obrisan);
					korisnici.add(musterija);
				}
			}
			reader.close();
		}
	catch(IOException e) {
		System.out.println("Doslo je do greske prilikom ucitavanja podataka");
	
		}
		return korisnici;
	}
	
	
	public  static void KorisniciWriter(ArrayList<Korisnik> korisnici) {
		String zaUpis = "";

		
		for (int i = 0; i < korisnici.size(); i++) {
			Object korisnik = korisnici.get(i);
			if(korisnik instanceof Administrator) {
				
				Administrator administrator = (Administrator) korisnik;
				
				zaUpis += administrator.getId() + "|" + "administrator" + "|" + 
				administrator.getIme() + "|" + administrator.getPrezime() + "|" + 
				administrator.getPol() + "|" + administrator.getAdresa() +
				"|" + administrator.getBrTelefona() + "|" + 
				administrator.getKorisnickoIme() + "|" +
				administrator.getLozinka() + "|" + administrator.getJmbg() +
				"|" + administrator.getPlata() + "|" + administrator.isObrisan() +"\n";
			}
			else if(korisnik instanceof Serviser) {
				
				Serviser serviser = (Serviser) korisnik;
				
				zaUpis += serviser.getId() + "|" + "serviser" + "|" + 
				serviser.getIme() + "|" + serviser.getPrezime() + "|" +
				serviser.getPol() + "|" + serviser.getAdresa() + "|" +
				serviser.getBrTelefona() + "|" + serviser.getKorisnickoIme() + 
				"|" + serviser.getLozinka() + "|" + serviser.getJmbg() + "|" + 
				serviser.getPlata() + "|" + serviser.getSpecijalizacija() +"|"+ serviser.isObrisan() +"\n";
			}
			else if(korisnik instanceof Musterija) {
				
				Musterija musterija = (Musterija) korisnik;
				
				zaUpis += musterija.getId() + "|" + "musterija" + "|" +
				musterija.getIme() + "|" + musterija.getPrezime() + "|" +
				musterija.getPol() + "|" + musterija.getAdresa() + 
				"|" + musterija.getBrTelefona() + "|" + 
				musterija.getKorisnickoIme() + "|" + musterija.getLozinka() +
				"|" + musterija.getJmbg() + "|" + musterija.getBrBodova() +"|" + musterija.isObrisan() + "\n";
			}
		
		}
		try {
			File file = new File("src/podaci/korisnici.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(zaUpis);
			writer.close();
		}
		catch(IOException e) {
			System.out.println("Greska prilikom ucitavanja datoteke: " + e.getMessage());
		}
	}
	
	
		}
				
