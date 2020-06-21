package entiteti;

import enumeracije.Pol;
import enumeracije.Specijalizacija;

public class Serviser extends Korisnik{
	
	private int plata;
	private Specijalizacija specijalizacija;
	
	public Serviser() {
		super();
		this.plata = 0;
		this.specijalizacija = Specijalizacija.AUTOELEKTRICAR;
	}
	
	public Serviser(String id, String ime, String prezime, Pol pol, String adresa, String brTelefona,String korisnickoIme, String lozinka, String jmbg, int plata, Specijalizacija specijalizacija, boolean obrisan) {
		super(id, ime, prezime, pol, adresa, brTelefona, korisnickoIme, lozinka, jmbg, obrisan);
		this.plata = plata;
		this.specijalizacija = specijalizacija;
	}
	
	public int getPlata() {
		return plata;
	}

	public void setPlata(int plata) {
		this.plata = plata;
	}

	public Specijalizacija getSpecijalizacija() {
		return specijalizacija;
	}

	public void setSpecijalizacija(Specijalizacija specijalizacija) {
		this.specijalizacija = specijalizacija;
	}

	@Override
	public String toString() {
		return "Serviser [plata=" + plata + ", specijalizacija=" + specijalizacija + ", id=" + id + ", pol=" + pol
				+ ", ime=" + ime + ", prezime=" + prezime + ", adresa=" + adresa + ", brTelefona=" + brTelefona
				+ ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + ", jmbg=" + jmbg + ", obrisan="
				+ obrisan + "]";
	}

	
	
}
