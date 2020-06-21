package entiteti;
import enumeracije.Pol;

public abstract class Korisnik {

	protected String id;
	protected Pol pol;
	protected String ime, prezime, adresa, brTelefona, korisnickoIme, lozinka;
	protected String jmbg;
	protected boolean obrisan;
	
	

	public Korisnik() {
		this.id = "";
		this.ime = "";
		this.prezime = "";
		this.pol = Pol.ZENSKI;
		this.adresa = "";
		this.brTelefona = "";
		this.korisnickoIme = "";
		this.lozinka = "";
		this.jmbg = "";
		this.obrisan = false;
	}
	

	public Korisnik(String id,String ime, String prezime, Pol pol, String adresa, String brTelefona,String korisnickoIme, String lozinka, String jmbg, boolean obrisan) {
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.pol = pol; 
		this.adresa = adresa; 
		this.brTelefona = brTelefona;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.jmbg = jmbg;
		this.obrisan = obrisan;
	}

	
	//get i set metode
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public Pol getPol() {
		return pol;
	}

	public void setPol(Pol pol) {
		this.pol = pol;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getBrTelefona() {
		return brTelefona;
	}

	public void setBrTelefona(String brTelefona) {
		this.brTelefona = brTelefona;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", pol=" + pol + ", ime=" + ime + ", prezime=" + prezime + ", adresa=" + adresa
				+ ", brTelefona=" + brTelefona + ", korisnickoIme=" + korisnickoIme + ", lozinka=" + lozinka + ", jmbg="
				+ jmbg + ", obrisan=" + obrisan + "]";
	}
	
	

	
	
}

