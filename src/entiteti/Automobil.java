package entiteti;

import enumeracije.Marka;
import enumeracije.Model;
import enumeracije.VrstaGoriva;

public class Automobil {
	private String id;
	private Musterija vlasnik;
	private Marka marka;
	private Model model;
	private int godinaProizvodnje;
	private String zapreminaMotora;
	private String snagaMotora;
	private VrstaGoriva vrstaGoriva;
	private boolean obrisan;
	
	public Automobil() {
		this.id = "";
		this.vlasnik = new Musterija();
		this.marka = Marka.FIAT;
		this.model = Model.C5;
		this.godinaProizvodnje = 0;
		this.zapreminaMotora = "";
		this.snagaMotora = "";
		this.vrstaGoriva = VrstaGoriva.DIZEL;
		this.obrisan = false;
	}
	
	public Automobil(String id, Musterija vlasnik, Marka marka, Model model, int godinaProizvodnje, String zapreminaMotora, String snagaMotora, VrstaGoriva vrstaGoriva, boolean obrisan) {
		this.id = id;
		this.vlasnik = vlasnik;
		this.marka = marka;
		this.model = model;
		this.godinaProizvodnje = godinaProizvodnje;
		this.zapreminaMotora = zapreminaMotora;
		this.snagaMotora = snagaMotora;
		this.vrstaGoriva = vrstaGoriva;
		this.obrisan = obrisan;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Musterija getVlasnik() {
		return vlasnik;
	}
	
	public String getVlasnikId() {
		return vlasnik.getId();
	}

	public void setVlasnik(Musterija vlasnik) {
		this.vlasnik = vlasnik;
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

	public int getGodinaProizvodnje() {
		return godinaProizvodnje;
	}

	public void setGodinaProizvodnje(int godinaProizvodnje) {
		this.godinaProizvodnje = godinaProizvodnje;
	}

	public String getZapreminaMotora() {
		return zapreminaMotora;
	}

	public void setZapreminaMotora(String zapreminaMotora) {
		this.zapreminaMotora = zapreminaMotora;
	}

	public String getSnagaMotora() {
		return snagaMotora;
	}

	public void setSnagaMotora(String snagaMotora) {
		this.snagaMotora = snagaMotora;
	}

	public VrstaGoriva getVrstaGoriva() {
		return vrstaGoriva;
	}

	public void setVrstaGoriva(VrstaGoriva vrstaGoriva) {
		this.vrstaGoriva = vrstaGoriva;
	}

	public boolean isObrisan() {
		return obrisan;
	}

	public void setObrisan(boolean obrisan) {
		this.obrisan = obrisan;
	}

	@Override
	public String toString() {
		return "Automobil [id=" + id + ", vlasnik=" + vlasnik + ", marka=" + marka + ", model=" + model
				+ ", godinaProizvodnje=" + godinaProizvodnje + ", zapreminaMotora=" + zapreminaMotora + ", snagaMotora="
				+ snagaMotora + ", vrstaGoriva=" + vrstaGoriva + ", obrisan=" + obrisan + "]";
	}
	
	

	
}

	