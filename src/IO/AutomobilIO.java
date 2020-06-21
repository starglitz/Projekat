package IO;
import enumeracije.Model;
import enumeracije.Marka;
import enumeracije.VrstaGoriva;
import entiteti.Musterija;
import entiteti.Povezivanje;
import entiteti.Automobil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



public class AutomobilIO {

	public static ArrayList<Automobil> AutomobilReader() {
		ArrayList<Automobil> automobili = new ArrayList<Automobil>();
		try {
			File file = new File("src/podaci/automobili.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] osobine = line.split("\\|");
				String id = osobine[0];
				String vlasnikId = osobine[1];
				String marka = osobine[2];
				String model = osobine[3];
				int godinaProizvodnje = Integer.parseInt(osobine[4]);
				String zapreminaMotora = osobine[5];
				String snagaMotora = osobine[6];
				String vrstaGoriva = osobine[7];	
				boolean obrisan = Boolean.parseBoolean(osobine[8]);
				Musterija vlasnikObj = Povezivanje.getMusterijaPoId(vlasnikId);
				
				Marka marka1 = Marka.valueOf(marka);
				Model model1 = Model.valueOf(model);
				VrstaGoriva vrstaGoriva1 = VrstaGoriva.valueOf(vrstaGoriva);
				
				
				Automobil automobil = new Automobil(id, vlasnikObj, marka1, model1, godinaProizvodnje, zapreminaMotora, snagaMotora, vrstaGoriva1, obrisan);
				automobili.add(automobil);
			}
			reader.close();
			
		}catch(IOException e) {
			System.out.println("Greska prilikom iscitavanja vrednosti iz fajla");
		}
		return automobili;
	}
	
	
	public static void AutomobilWriter(ArrayList<Automobil> automobili) {
		String zaUpis = "";
		for (Automobil automobil : automobili) {
			zaUpis += automobil.getId() +"|"+ automobil.getVlasnikId() +"|"+ 
		automobil.getMarka() +"|"+ automobil.getModel() +"|"+ automobil.getGodinaProizvodnje() +
		"|"+ automobil.getZapreminaMotora()  +"|"+automobil.getSnagaMotora() +
		"|"+ automobil.getVrstaGoriva() +"|"+automobil.isObrisan() +"\n";
		}
		try {
			File file = new File("src/podaci/automobili.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(zaUpis);
			writer.close();
		}
		catch(IOException e) {
			System.out.println("Greska prilikom ucitavanja datoteke: " + e.getMessage());
		}
	}
	
	
}
