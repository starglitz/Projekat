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

public class PrikazAdministratora extends JFrame {
	private JTable tabelaAdministratora;
	private DefaultTableModel tableModel;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnDodaj = new JButton("Dodaj novog korisnika");
	private JButton btnIzmeni = new JButton("Izmeni korisnika");
	private JButton btnIzbrisi = new JButton("Obrisi korisnika");
	
	public PrikazAdministratora(Administrator administrator) {
		setTitle("Prikaz administratora");
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
		String[] zaglavlja = new String[] {"ID" ,"Ime", "Prezime", "Pol", "Adresa", "Broj telefona", "Korisnicko ime", "Lozinka", "JMBG", "Plata"};
		Object[][] sadrzaj = new Object[UcitavanjePodataka.getNeobrisaniAdministratori().size()][zaglavlja.length];
		
		for(int i=0; i<UcitavanjePodataka.getNeobrisaniAdministratori().size(); i++) {
			
			Administrator administratorObj = UcitavanjePodataka.getNeobrisaniAdministratori().get(i);
			
			sadrzaj[i][0] = administratorObj.getId();
			sadrzaj[i][1] = administratorObj.getIme();
			sadrzaj[i][2] = administratorObj.getPrezime();
			sadrzaj[i][3] = administratorObj.getPol();
			sadrzaj[i][4] = administratorObj.getAdresa();
			sadrzaj[i][5] = administratorObj.getBrTelefona();
			sadrzaj[i][6] = administratorObj.getKorisnickoIme();
			sadrzaj[i][7] = administratorObj.getLozinka();
			sadrzaj[i][8] = administratorObj.getJmbg();
			sadrzaj[i][9] = administratorObj.getPlata();
		}
		tableModel = new DefaultTableModel(sadrzaj, zaglavlja);
		tabelaAdministratora = new JTable(tableModel);
		
		tabelaAdministratora.setRowSelectionAllowed(true);
		tabelaAdministratora.setColumnSelectionAllowed(false);
		tabelaAdministratora.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaAdministratora.setDefaultEditor(Object.class, null);
		tabelaAdministratora.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(tabelaAdministratora);
		add(scrollPane, BorderLayout.CENTER);
	
	}
	
	public void initActions(Administrator administrator) {
		btnIzbrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaAdministratora.getSelectedRow();
				//ako je minus 1 znaci da nijedan nije izabran red u tabeli, uvek je takva provera
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String administratorId = tableModel.getValueAt(red, 0).toString();
					Administrator administratorObj = Povezivanje.getAdministratorPoId(administratorId);
					int izbor = JOptionPane.showConfirmDialog(null, 
							"Da li ste sigurni da zelite da obrisete administratora?", 
							 " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
					if(izbor == JOptionPane.YES_OPTION) {
						UcitavanjePodataka.deleteKorisnik(administratorObj);
						tableModel.removeRow(red);
						KorisnikIO.KorisniciWriter(UcitavanjePodataka.getKorisnici());
					}
				}	
			}
		});
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdministratorDodavanjeIzmena dodavanje = new AdministratorDodavanjeIzmena(administrator, null);
				dodavanje.setVisible(true);
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaAdministratora.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String adminId = tableModel.getValueAt(red, 0).toString();
					Administrator adminObj = Povezivanje.getAdministratorPoId(adminId);
					
					if(adminObj == null) {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja administratora sa tim korisnickim imenom", "Greska", JOptionPane.WARNING_MESSAGE);
					}else {
						
						AdministratorDodavanjeIzmena izmena = new AdministratorDodavanjeIzmena(administrator, adminObj);
						izmena.setVisible(true);
					}
				}
			}
		});
	}
}

