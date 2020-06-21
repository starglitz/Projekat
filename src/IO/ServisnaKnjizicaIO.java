package IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import entiteti.Automobil;
import entiteti.Deo;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.ServisnaKnjizica;

public class ServisnaKnjizicaIO {
	public static ArrayList<ServisnaKnjizica> ServisnaKnjizicaReader() {
		ArrayList<ServisnaKnjizica> knjizice = new ArrayList<ServisnaKnjizica>();
		try {
			File file = new File("src/podaci/knjizice.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] osobine = line.split("\\|");
				String id = osobine[0];
				String idAutomobila = osobine[1];
				Automobil automobil = Povezivanje.getAutomobilPoId(idAutomobila);
				boolean obrisan = Boolean.parseBoolean(osobine[3]);
				
				String[] listaServisa = osobine[2].split(",");
				ArrayList<ServisAutomobila>listaServisaObj = Povezivanje.getServisPoId(listaServisa);
				
				
			    ServisnaKnjizica knjizica = new ServisnaKnjizica(id, automobil, listaServisaObj, obrisan);
			    knjizice.add(knjizica);
			}
		reader.close();
		}catch(IOException e) {
			System.out.println("Greska prilikom iscitavanja vrednosti iz fajla");
		}
		return knjizice;
		}
	
	
	public static void ServisnaKnjizicaWriter(ArrayList<ServisnaKnjizica> knjizice) {
		String zaUpis = "";
		for (ServisnaKnjizica knjizica : knjizice) {
			ArrayList<String> listaServisId = knjizica.getListaServisaId();
			zaUpis += knjizica.getId()+ "|" + knjizica.getAutomobilId() + "|" + String.join(",", listaServisId)+"|" +knjizica.isObrisan()+"\n";
					try {
			File file = new File("src/podaci/knjizice.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(zaUpis);
			writer.close();
	
					}catch(IOException e) {
			System.out.println("Greska prilikom ucitavanja datoteke: " + e.getMessage());
		}
	
}}
	
	

}
