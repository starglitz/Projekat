package IO;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import entiteti.Automobil;
import entiteti.Deo;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.Serviser;
import enumeracije.StatusServisa;

public class ServisAutomobilaIO {
	
	public static ArrayList<ServisAutomobila> ServisAutomobilaReader() {
		ArrayList<ServisAutomobila> servisi = new ArrayList<ServisAutomobila>();
		try {
			File file = new File("src/podaci/servisi.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] osobine = line.split("\\|");
				String id = osobine[0];
				String idAutomobila = osobine[1];
				String opis = osobine[2];
				StatusServisa status = StatusServisa.valueOf(osobine[3].strip());
				boolean obrisan = Boolean.parseBoolean(osobine[4]);
				Automobil automobil = Povezivanje.getAutomobilPoId(idAutomobila);
				if (osobine.length == 5) {
					ServisAutomobila servis = new ServisAutomobila(id, automobil,opis,status, obrisan);
					servisi.add(servis);
				}
				else {
					String idServisera = osobine[5];
					GregorianCalendar termin = ServisAutomobila.StringToGregorian(osobine[6]);
					String deloviString = osobine[7];
		
					GregorianCalendar danas = new GregorianCalendar();
					int troskovi = Integer.parseInt(osobine[8]);
					Serviser serviser = Povezivanje.getServiserPoId(idServisera);
					
					String[] delovi = deloviString.split(",");
					ArrayList<Deo> listaDelova = Povezivanje.getDeoPoId(delovi);
					ServisAutomobila servis = new ServisAutomobila(id, automobil, opis, status, obrisan, serviser, termin, listaDelova, troskovi);
					if(servis.getTermin().compareTo(danas) < 0 || servis.getTermin().compareTo(danas) == 0) {
						servis.setStatus(StatusServisa.ZAPOCET);
					}
					servisi.add(servis);
				}
				
			}
			
		reader.close();	
		ServisAutomobilaWriter(servisi);	
		}
		catch(IOException e) {
			System.out.println("Greska prilikom ucitavanja podataka");
		}
	
		return servisi;
	}
			
	
	
	
	
	
	public  static void ServisAutomobilaWriter(ArrayList<ServisAutomobila> servisi) {
		String zaUpis = "";
		for (ServisAutomobila servis : servisi) {
			if(servis.getServiser() == null) {
				zaUpis += servis.getId() + "|" + servis.getAutomobilId() + "|" + servis.getOpis() + "|" + servis.getStatus() 
				+ "|" + servis.isObrisan() + "\n";
			}
			else {
				ArrayList<String> listaDelovaId = servis.getListaIdDelova();
				zaUpis += servis.getId() + "|" + servis.getAutomobilId() + "|" + servis.getOpis() +
					"|" + servis.getStatus() + "|" + servis.isObrisan() + "|" + 
					servis.getServiserId() +"|" + servis.getTerminSimpleDate()+"|" +String.join(",", listaDelovaId) +"|"+servis.getTroskovi() + "\n";
			}
			}
		try {
			File file = new File("src/podaci/servisi.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(zaUpis);
			writer.close();
		}
		catch(IOException e) {
			System.out.println("Greska prilikom ucitavanja datoteke: " + e.getMessage());
		}
		}
		

		
}


