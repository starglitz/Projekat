package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import entiteti.Administrator;
import entiteti.Musterija;
import entiteti.Serviser;
import gui.Administrator.PrikazAutomobila;
import gui.Administrator.PrikazDelova;
import gui.Administrator.PrikazKnjizica;
import gui.Administrator.PrikazKorisnika;
import gui.Administrator.PrikazServisa;
import gui.Administrator.ZahteviZaServis;
import net.miginfocom.swing.MigLayout;

public class AdministratorProzor extends JFrame {
	
	private JLabel lblPoruka = new JLabel("Uspesno ste se prijavili! ");
	private JButton btnKorisnici = new JButton("Upravljaj korisnicima");
	private JButton btnAutomobili = new JButton("Upravljaj automobilima");
	private JButton btnServisi = new JButton("Upravljaj servisima");
	private JButton btnDelovi = new JButton("Upravljaj delovima");
	private JButton btnKnjizice = new JButton("Upravljaj knjizicama");
	private JButton btnPotvrdaServisa = new JButton("Zahtevi za servise");
	private JButton btnKreirajSimetricni = new JButton("Kreiranje simetricnog dela");
	JLabel lblOsobaLogo = new JLabel(new ImageIcon("src/slike/osoba.png"));
	private JButton btnIzadji = new JButton("Izadji iz aplikacije");
	private JButton btnOdjaviSe = new JButton("Odjavi se");
	
	public AdministratorProzor(Administrator administrator) {
		setTitle("Meni za administratora: " + administrator.getIme() + " " + administrator.getPrezime());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(administrator);
		initActions(administrator);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Administrator administrator) {
		

		
		MigLayout mig = new MigLayout("wrap 3", "[center]10[center][left][c]", "[center]10[center]15[center]15[center]20[center][b]");
		setLayout(mig);
		add(lblPoruka, "span 2");
		add(lblOsobaLogo, "span 1 5");
		add(btnKorisnici);
		add(btnAutomobili);
		add(btnServisi);
		add(btnDelovi);
		add(btnKnjizice);
		add(btnPotvrdaServisa);
		add(btnOdjaviSe);
		add(btnIzadji);
		
	}	
		
		
	public void initActions(Administrator administrator) {
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdministratorProzor.this.dispose();
				AdministratorProzor.this.setVisible(false);
			}
		});	
		
		btnOdjaviSe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdministratorProzor.this.dispose();
				AdministratorProzor.this.setVisible(false);
				
				Login login = new Login();
				login.setVisible(true);
			}
		});
		
		btnKorisnici.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazKorisnika prikazK = new PrikazKorisnika(administrator);
				prikazK.setVisible(true);
			}
		});
		
		btnDelovi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazDelova prikazD = new PrikazDelova(administrator);
				prikazD.setVisible(true);
				
			}
		});
		
		btnAutomobili.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazAutomobila prikazA = new PrikazAutomobila(administrator);
				prikazA.setVisible(true);
				
			}
		});
		
		btnServisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazServisa prikazS = new PrikazServisa(administrator);
				prikazS.setVisible(true);
				
			}
		});
		
		btnKnjizice.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazKnjizica prikazKK = new PrikazKnjizica(administrator);
				prikazKK.setVisible(true);
				
			}
		});
		
		btnPotvrdaServisa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ZahteviZaServis zahtev = new ZahteviZaServis(administrator);
				zahtev.setVisible(true);
				
			}
		});
	}
}
