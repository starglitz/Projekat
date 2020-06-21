package gui.Administrator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import IO.AutomobilIO;
import IO.ServisAutomobilaIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Automobil;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.Serviser;
import gui.Serviser.IzmenaServisa;
import gui.Serviser.KreiranjeServisa;

public class PrikazServisa extends JFrame {
	private JTable tabelaServisa;
	private DefaultTableModel tableModel;
	private JButton btnDodaj = new JButton("Dodaj novi servis");
	private JButton btnIzmeni = new JButton("Izmeni servis");
	private JButton btnObrisi = new JButton("Obrisi servis");
	private JToolBar mainToolbar = new JToolBar();
	
	public PrikazServisa(Administrator admin) {
		setTitle("Prikaz servisa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(admin);
		initActions(admin);
		setSize(1000,300);
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Administrator admin) {	

		mainToolbar.add(btnIzmeni);
		mainToolbar.add(btnDodaj);
		mainToolbar.add(btnObrisi);
		
		
		add(mainToolbar, BorderLayout.NORTH);
		
		String[] zaglavlja = new String[] {"ID servisa", "ID automobila","Serviser", "Termin", "Opis", "Lista delova", "Status", "Troskovi"};
		
		ArrayList<ServisAutomobila> neobrServisi = UcitavanjePodataka.getNeobrisaniServisi();
		ArrayList<ServisAutomobila> potpuniServisi = new ArrayList<ServisAutomobila>();
		
		for(ServisAutomobila servis : neobrServisi) {
			if(servis.getServiser() != null) {
				potpuniServisi.add(servis);
			}
		}
		
		Object[][] sadrzaj = new Object[potpuniServisi.size()][zaglavlja.length];
		
		for(int i=0; i<potpuniServisi.size();i++) {
			ServisAutomobila servis = potpuniServisi.get(i);
			Serviser serviser = servis.getServiser();
			String serviserIme = serviser.getIme() + " " + serviser.getPrezime();
			
			sadrzaj[i][0] = servis.getId();
			sadrzaj[i][1] = servis.getAutomobilId();
			sadrzaj[i][2] = serviserIme;
			sadrzaj[i][3] = servis.getTerminSimpleDate();
			sadrzaj[i][4] = servis.getOpis();
			sadrzaj[i][5] = servis.getListaIdDelova();
			sadrzaj[i][6] = servis.getStatus();
			sadrzaj[i][7] = servis.getTroskovi();
		
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
	
	public void initActions(Administrator admin) {
		
		btnObrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaServisa.getSelectedRow();
				//ako je minus 1 znaci da nijedan nije izabran red u tabeli, uvek je takva provera
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else { 
					String ServisId = tableModel.getValueAt(red, 0).toString();
					ServisAutomobila servis = Povezivanje.getServisPoId(ServisId);
					int izbor = JOptionPane.showConfirmDialog(null, 
							"Da li ste sigurni da zelite da obrisete servis?", 
							 " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
					if(izbor == JOptionPane.YES_OPTION) {
						UcitavanjePodataka.deleteServis(servis);
						tableModel.removeRow(red);
						ServisAutomobilaIO.ServisAutomobilaWriter(UcitavanjePodataka.getServisi());
					}
				}	
			}
		});
		
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ServisDodavanjeIzmena kreiranje = new ServisDodavanjeIzmena(admin, null);
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
						ServisDodavanjeIzmena izmena = new ServisDodavanjeIzmena(admin, servis);
						izmena.setVisible(true);
					}
				}
			}
		});
		
		
	}

	
}
