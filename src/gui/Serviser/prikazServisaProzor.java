package gui.Serviser;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import IO.KorisnikIO;
import IO.ServisAutomobilaIO;
import IO.UcitavanjePodataka;
import entiteti.Automobil;
import entiteti.Deo;
import entiteti.Musterija;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.Serviser;
import enumeracije.StatusServisa;

import net.miginfocom.swing.MigLayout;


public class prikazServisaProzor extends JFrame {
	private JTable tabelaServisa;
	private DefaultTableModel tableModel;
	private JButton btnZavrsi = new JButton("Zavrsi servis");
	private JButton btnDodaj = new JButton("Dodaj novi servis");
	private JButton btnIzmeni = new JButton("Izmeni servis");
	private JToolBar mainToolbar = new JToolBar();
	
	 
	
	public prikazServisaProzor(Serviser serviser) {
		setTitle("Prikaz servisa servisera: " + serviser.getIme() +" "+ serviser.getPrezime());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(serviser);
		initActions(serviser);
		setSize(900,300);
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Serviser serviser) {	

		mainToolbar.add(btnZavrsi);
		mainToolbar.add(btnIzmeni);
		mainToolbar.add(btnDodaj);
		
		
		add(mainToolbar, BorderLayout.NORTH);
		
		String[] zaglavlja = new String[] {"ID servisa", "ID automobila", "Termin", "Opis", "Lista delova", "Status", "Troskovi"};
		ArrayList<ServisAutomobila> servisiServisera = Povezivanje.getServisiPoServiseru(serviser);
		
		
		Object[][] sadrzaj = new Object[servisiServisera.size()][zaglavlja.length];
		
		for(int i=0; i<servisiServisera.size();i++) {
			ServisAutomobila servis = servisiServisera.get(i);
			sadrzaj[i][0] = servis.getId();
			sadrzaj[i][1] = servis.getAutomobilId();
			sadrzaj[i][2] = servis.getTerminSimpleDate();
			sadrzaj[i][3] = servis.getOpis();
			sadrzaj[i][4] = servis.getListaIdDelova();
			sadrzaj[i][5] = servis.getStatus();
			sadrzaj[i][6] = servis.getTroskovi();
		}
		tableModel = new DefaultTableModel(sadrzaj,zaglavlja);
		tabelaServisa = new JTable(tableModel);
		tabelaServisa.setRowSelectionAllowed(true);
		tabelaServisa.setColumnSelectionAllowed(false);
		tabelaServisa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaServisa.setDefaultEditor(Object.class, null);
		tabelaServisa.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(tabelaServisa);
	
		
		
		add(scrollPane, BorderLayout.CENTER);
	
	}
	
	public void initActions(Serviser serviser) {
		btnZavrsi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaServisa.getSelectedRow();
				//ako je minus 1 znaci da nijedan nije izabran red u tabeli, uvek je takva provera
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String status = tableModel.getValueAt(red, 5).toString();
					//System.out.println(status);
					if(status.equals("ZAVRSEN")) {
						JOptionPane.showMessageDialog(null, "Ovaj servis je vec zavrsen.", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					else {
						String id = tableModel.getValueAt(red, 0).toString();
						statusZavrsen(id);
						cenaServisa(id);
						
							}
						}
			}
		});
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				KreiranjeServisa kreiranje = new KreiranjeServisa(serviser);
				kreiranje.setVisible(true);
				
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaServisa.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String servisID = tableModel.getValueAt(red, 0).toString();
					ServisAutomobila servis = Povezivanje.getServisPoId(servisID);
					
					if(servis == null) {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja servisa", "Greska", JOptionPane.WARNING_MESSAGE);
					}else {
						IzmenaServisa izmena = new IzmenaServisa(serviser, servis);
						izmena.setVisible(true);
					}
				}
			}
		});
	}
	
	public static void statusZavrsen(String id) {
		ArrayList<ServisAutomobila> servisi = UcitavanjePodataka.getServisi();
		for(ServisAutomobila servis : servisi) {
			if(servis.getId().equals(id)) {
				servis.setStatus(StatusServisa.ZAVRSEN);
			}
		}
		ServisAutomobilaIO.ServisAutomobilaWriter(servisi);
	}
	
	public void cenaServisa(String servisId) {
		ServisAutomobila servis = Povezivanje.getServisPoId(servisId);
		double cenaServisa = 0;
		for(Deo deo : servis.getListaDelova()) {
			cenaServisa += deo.getCena();
		}
		cenaServisa += servis.getTroskovi();
		Musterija musterija = Povezivanje.getMusterijaPoId(servis.getAutomobil().getVlasnikId());
		
		int izbor = JOptionPane.showConfirmDialog(null, "Da li zelite da uracunate musterijine nagradne bodove?", "Upotreba nagradnih bodova",JOptionPane.YES_NO_OPTION);
		if(izbor == JOptionPane.YES_OPTION) {
			cenaServisa = Math.round(cenaServisa * ( 1 -(musterija.getBrBodova() * 0.02)));
			musterija.setBrBodova(0);
		}
		if(izbor == JOptionPane.NO_OPTION) {
			if(musterija.getBrBodova() < 11) {
			musterija.setBrBodova(musterija.getBrBodova() + 1);
		}
		}
		KorisnikIO.KorisniciWriter(UcitavanjePodataka.getKorisnici());
		JOptionPane.showMessageDialog(null, "Ukupna cena servisa: " + cenaServisa, "Cena servisa", JOptionPane.PLAIN_MESSAGE);
	}
	

}
