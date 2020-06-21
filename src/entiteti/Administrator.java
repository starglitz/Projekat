package entiteti;

import enumeracije.Pol;

public class Administrator extends Korisnik{
	
	private int plata;
	
	public Administrator() {
		super();
		this.plata = 0;
	}
	
	public Administrator(String id, String ime, String prezime, Pol pol, String adresa, String brTelefona,String korisnickoIme, String lozinka, String jmbg, int plata, boolean obrisan) {
		super(id, ime, prezime, pol, adresa, brTelefona, korisnickoIme, lozinka, jmbg, obrisan);
		this.plata = plata;
	}
	

	public int getPlata() {
		return plata;
	}

	public void setPlata(int plata) {
		this.plata = plata;
	}

	@Override
	public String toString() {
		return "Administrator [plata=" + plata + ", id=" + id + ", pol=" + pol + ", ime=" + ime + ", prezime=" + prezime
				+ ", adresa=" + adresa + ", brTelefona=" + brTelefona + ", korisnickoIme=" + korisnickoIme
				+ ", lozinka=" + lozinka + ", jmbg=" + jmbg + ", obrisan=" + obrisan + "]";
	}

	


}
