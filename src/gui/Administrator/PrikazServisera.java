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

import IO.KorisnikIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Musterija;
import entiteti.Povezivanje;
import entiteti.Serviser;

public class PrikazServisera extends JFrame {

	private JTable tabelaServisera;
	private DefaultTableModel tableModel;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnDodaj = new JButton("Dodaj novog korisnika");
	private JButton btnIzmeni = new JButton("Izmeni korisnika");
	private JButton btnIzbrisi = new JButton("Obrisi korisnika");
	
	public PrikazServisera(Administrator administrator) {
		setTitle("Prikaz korisnika");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(administrator);
		initActions(administrator);
		setSize(1000,400);
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Administrator administrator) {
		mainToolbar.add(btnDodaj);
		mainToolbar.add(btnIzmeni);
		mainToolbar.add(btnIzbrisi);
		
		add(mainToolbar, BorderLayout.NORTH);
		String[] zaglavlja = new String[] {"ID" ,"Ime", "Prezime", "Pol", "Adresa", "Broj telefona", "Korisnicko ime", "Lozinka", "JMBG", "Plata", "Specijalizacija"};
		Object[][] sadrzaj = new Object[UcitavanjePodataka.getNeobrisaniServiseri().size()][zaglavlja.length];
		
		for(int i=0; i<UcitavanjePodataka.getNeobrisaniServiseri().size(); i++) {
			
			Serviser serviser = UcitavanjePodataka.getNeobrisaniServiseri().get(i);
			sadrzaj[i][0] = serviser.getId();
			sadrzaj[i][1] = serviser.getIme();
			sadrzaj[i][2] = serviser.getPrezime();
			sadrzaj[i][3] = serviser.getPol();
			sadrzaj[i][4] = serviser.getAdresa();
			sadrzaj[i][5] = serviser.getBrTelefona();
			sadrzaj[i][6] = serviser.getKorisnickoIme();
			sadrzaj[i][7] = serviser.getLozinka();
			sadrzaj[i][8] = serviser.getJmbg();
			sadrzaj[i][9] = serviser.getPlata();
			sadrzaj[i][10] = serviser.getSpecijalizacija();
		}
		tableModel = new DefaultTableModel(sadrzaj, zaglavlja);
		tabelaServisera = new JTable(tableModel);
		
		tabelaServisera.setRowSelectionAllowed(true);
		tabelaServisera.setColumnSelectionAllowed(false);
		tabelaServisera.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaServisera.setDefaultEditor(Object.class, null);
		tabelaServisera.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(tabelaServisera);
		add(scrollPane, BorderLayout.CENTER);
	
	}
	
	public void initActions(Administrator administrator) {
		btnIzbrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaServisera.getSelectedRow();
				//ako je minus 1 znaci da nijedan nije izabran red u tabeli, uvek je takva provera
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String serviserId = tableModel.getValueAt(red, 0).toString();
					Serviser serviser = Povezivanje.getServiserPoId(serviserId);
					int izbor = JOptionPane.showConfirmDialog(null, 
							"Da li ste sigurni da zelite da obrisete servisera?", 
							 " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
					if(izbor == JOptionPane.YES_OPTION) {
						UcitavanjePodataka.deleteKorisnik(serviser);
						tableModel.removeRow(red);
						KorisnikIO.KorisniciWriter(UcitavanjePodataka.getKorisnici());
					}
				}	
			}
		});
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServiserDodavanjeIzmena dodavanje = new ServiserDodavanjeIzmena(administrator, null);
				dodavanje.setVisible(true);
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaServisera.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String serviserId = tableModel.getValueAt(red, 0).toString();
					Serviser serviser = Povezivanje.getServiserPoId(serviserId);
					if(serviser == null) {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja administratora sa tim korisnickim imenom", "Greska", JOptionPane.WARNING_MESSAGE);
					}else {
						
						ServiserDodavanjeIzmena izmena = new ServiserDodavanjeIzmena(administrator, serviser);
						izmena.setVisible(true);
					}
				}
			}
		});
	}
}
