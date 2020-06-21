package main;

import java.util.ArrayList;
import IO.UcitavanjePodataka;
import entiteti.Automobil;
import entiteti.Deo;
import entiteti.Korisnik;
import entiteti.ServisAutomobila;
import entiteti.ServisnaKnjizica;

import gui.Login;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<Korisnik> korisnici = UcitavanjePodataka.sviKorisnici;
		ArrayList<Deo> delovi = UcitavanjePodataka.sviDelovi;
		ArrayList<Automobil> automobili = UcitavanjePodataka.sviAutomobili;
		ArrayList<ServisAutomobila> servisi = UcitavanjePodataka.sviServisi;
		ArrayList<ServisnaKnjizica> knjizice = UcitavanjePodataka.sveKnjizice;
		
		Login login = new Login();
		login.setVisible(true);
		
	}
}