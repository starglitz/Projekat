package gui.Administrator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import IO.AutomobilIO;
import IO.KorisnikIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Automobil;
import entiteti.Musterija;
import entiteti.Povezivanje;
import enumeracije.Marka;
import enumeracije.Model;
import enumeracije.VrstaGoriva;

public class PrikazAutomobila extends JFrame {
	private JTable tabelaAutomobila;
	private DefaultTableModel tableModel;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnDodaj = new JButton("Dodaj novi automobil");
	private JButton btnIzmeni = new JButton("Izmeni automobil");
	private JButton btnIzbrisi = new JButton("Obrisi automobil");
	
	public PrikazAutomobila(Administrator administrator) {
		setTitle("Prikaz automobila");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(administrator);
		initActions(administrator);
		setSize(800,400);
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Administrator administrator) {
		mainToolbar.add(btnDodaj);
		mainToolbar.add(btnIzmeni);
		mainToolbar.add(btnIzbrisi);
		
		add(mainToolbar, BorderLayout.NORTH);
		String[] zaglavlja = new String[] {"ID" ,"Vlasnik", "Marka", "Model", "Godina proizvodnje", "Zapremina motora", "Snaga motora", "Vrsta goriva"};
		Object[][] sadrzaj = new Object[UcitavanjePodataka.getNeobrisaniAutomobili().size()][zaglavlja.length];
		
		for(int i=0; i<UcitavanjePodataka.getNeobrisaniAutomobili().size(); i++) {
			Automobil auto = UcitavanjePodataka.getNeobrisaniAutomobili().get(i);
			Musterija vlasnik = auto.getVlasnik();
			String imeIPrezime = vlasnik.getIme() + " " + vlasnik.getPrezime();
			sadrzaj[i][0] = auto.getId();
			sadrzaj[i][1] = imeIPrezime;
			sadrzaj[i][2] = auto.getMarka();
			sadrzaj[i][3] = auto.getModel();
			sadrzaj[i][4] = auto.getGodinaProizvodnje();
			sadrzaj[i][5] = auto.getZapreminaMotora();
			sadrzaj[i][6] = auto.getSnagaMotora();
			sadrzaj[i][7] = auto.getVrstaGoriva();
		}
		tableModel = new DefaultTableModel(sadrzaj, zaglavlja);
		tabelaAutomobila = new JTable(tableModel);
		
		tabelaAutomobila.setRowSelectionAllowed(true);
		tabelaAutomobila.setColumnSelectionAllowed(false);
		tabelaAutomobila.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaAutomobila.setDefaultEditor(Object.class, null);
		tabelaAutomobila.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(tabelaAutomobila);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void initActions(Administrator administrator) {
		btnIzbrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaAutomobila.getSelectedRow();
				//ako je minus 1 znaci da nijedan nije izabran red u tabeli, uvek je takva provera
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else { 
					String automobilId = tableModel.getValueAt(red, 0).toString();
					Automobil auto = Povezivanje.getAutomobilPoId(automobilId);
					int izbor = JOptionPane.showConfirmDialog(null, 
							"Da li ste sigurni da zelite da obrisete automobil?", 
							 " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
					if(izbor == JOptionPane.YES_OPTION) {
						UcitavanjePodataka.deleteAutomobil(auto);
						tableModel.removeRow(red);
						AutomobilIO.AutomobilWriter(UcitavanjePodataka.getAutomobili());
					}
				}	
			}
		});
		
		btnDodaj.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AutomobilDodavanjeIzmena dodaj = new AutomobilDodavanjeIzmena(administrator, null);
				dodaj.setVisible(true);
				
			}
		});
		
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaAutomobila.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String automobilId = tableModel.getValueAt(red, 0).toString();
					Automobil auto = Povezivanje.getAutomobilPoId(automobilId);
					if(auto == null) {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja automobila sa tim ID", "Greska", JOptionPane.WARNING_MESSAGE);
					}else {
						
						AutomobilDodavanjeIzmena izmena = new AutomobilDodavanjeIzmena(administrator, auto);
						izmena.setVisible(true);
					}
				}
			}
		});
		
	}
}
