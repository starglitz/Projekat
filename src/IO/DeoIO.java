package IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.Deo;
import enumeracije.Marka;
import enumeracije.Model;

public class DeoIO {
	
	public static ArrayList<Deo> DeloviReader() {
		ArrayList<Deo> sviDelovi = new ArrayList<Deo>();
		try {
			File file = new File("src/podaci/delovi.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] osobine = line.split("\\|");
				String id = osobine[0];
				Marka marka = Marka.valueOf(osobine[1]);
				Model model = Model.valueOf(osobine[2]);
				String naziv = osobine[3];
				int cena = Integer.parseInt(osobine[4]);
				boolean obrisan = Boolean.parseBoolean(osobine[5]);
				
				Deo deo = new Deo(id, marka, model, naziv, cena, obrisan);
				sviDelovi.add(deo);
			}
			reader.close();
			
		}catch(IOException e) {
			System.out.println("Greska prilikom iscitavanja vrednosti iz fajla");
		}
		return sviDelovi;
	}
	
	
	public static void DeoWriter(ArrayList<Deo> delovi) {
		String zaUpis = "";
		for (Deo deo : delovi) {
			zaUpis += deo.getId() +"|"+ deo.getMarka() +"|"+ deo.getModel() +"|"+ deo.getNaziv() +"|"+ deo.getCena() +"|" + deo.isObrisan() + "\n";
		}
		try {
			File file = new File("src/podaci/delovi.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(zaUpis);
			writer.close();
		}
		catch(IOException e) {
			System.out.println("Greska prilikom ucitavanja datoteke: " + e.getMessage());
		}
		
	}
}
