package entiteti;


import enumeracije.Pol;

public class Musterija extends Korisnik{
	private int brBodova;
	
	public Musterija() {
		super();
		this.brBodova = 0;		
	}
	
	public Musterija(String id, String ime, String prezime, Pol pol, String adresa, String brTelefona,String korisnickoIme, String lozinka, String jmbg, int brBodova, boolean obrisan) {
		super(id, ime, prezime, pol, adresa, brTelefona, korisnickoIme, lozinka, jmbg, obrisan);
		this.brBodova = brBodova;
	}
	

	public int getBrBodova() {
		return brBodova;
	}

	public void setBrBodova(int brBodova) {
		this.brBodova = brBodova;
	}

	@Override
	public String toString() {
		return "Musterija [brBodova=" + brBodova + ", id=" + id + ", pol=" + pol + ", ime=" + ime + ", prezime="
				+ prezime + ", adresa=" + adresa + ", brTelefona=" + brTelefona + ", korisnickoIme=" + korisnickoIme
				+ ", lozinka=" + lozinka + ", jmbg=" + jmbg + ", obrisan=" + obrisan + "]";
	}

	
	


	

	


	
}
