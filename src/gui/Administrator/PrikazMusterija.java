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
import entiteti.Korisnik;
import entiteti.Musterija;
import entiteti.Povezivanje;



public class PrikazMusterija extends JFrame {
	
	private JTable tabelaMusterija;
	private DefaultTableModel tableModel;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnDodaj = new JButton("Dodaj novog korisnika");
	private JButton btnIzmeni = new JButton("Izmeni korisnika");
	private JButton btnIzbrisi = new JButton("Obrisi korisnika");
	
	public PrikazMusterija(Administrator administrator) {
		setTitle("Prikaz musterija");
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
		String[] zaglavlja = new String[] {"ID" ,"Ime", "Prezime", "Pol", "Adresa", "Broj telefona", "Korisnicko ime", "Lozinka", "JMBG", "Broj bodova"};
		Object[][] sadrzaj = new Object[UcitavanjePodataka.getNeobrisaneMusterije().size()][zaglavlja.length];
		
		for(int i=0; i<UcitavanjePodataka.getNeobrisaneMusterije().size(); i++) {
			
			Musterija musterija = UcitavanjePodataka.getNeobrisaneMusterije().get(i);
			sadrzaj[i][0] = musterija.getId();
			sadrzaj[i][1] = musterija.getIme();
			sadrzaj[i][2] = musterija.getPrezime();
			sadrzaj[i][3] = musterija.getPol();
			sadrzaj[i][4] = musterija.getAdresa();
			sadrzaj[i][5] = musterija.getBrTelefona();
			sadrzaj[i][6] = musterija.getKorisnickoIme();
			sadrzaj[i][7] = musterija.getLozinka();
			sadrzaj[i][8] = musterija.getJmbg();
			sadrzaj[i][9] = musterija.getBrBodova();
		}
		tableModel = new DefaultTableModel(sadrzaj, zaglavlja);
		tabelaMusterija = new JTable(tableModel);
		
		tabelaMusterija.setRowSelectionAllowed(true);
		tabelaMusterija.setColumnSelectionAllowed(false);
		tabelaMusterija.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaMusterija.setDefaultEditor(Object.class, null);
		tabelaMusterija.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(tabelaMusterija);
		add(scrollPane, BorderLayout.CENTER);
	
	}
	
	public void initActions(Administrator administrator) {
		btnIzbrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaMusterija.getSelectedRow();
				//ako je minus 1 znaci da nijedan nije izabran red u tabeli, uvek je takva provera
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String musterijaId = tableModel.getValueAt(red, 0).toString();
					Musterija musterija = Povezivanje.getMusterijaPoId(musterijaId);
					int izbor = JOptionPane.showConfirmDialog(null, 
							"Da li ste sigurni da zelite da obrisete musteriju?", 
							 " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
					if(izbor == JOptionPane.YES_OPTION) {
						UcitavanjePodataka.deleteKorisnik(musterija);
						tableModel.removeRow(red);
						KorisnikIO.KorisniciWriter(UcitavanjePodataka.getKorisnici());
					}
				}	
			}
		});
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MusterijaDodavanjeIzmena dodavanje = new MusterijaDodavanjeIzmena(administrator, null);
				dodavanje.setVisible(true);
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaMusterija.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String musterijaId = tableModel.getValueAt(red, 0).toString();
					Musterija musterija = Povezivanje.getMusterijaPoId(musterijaId);
					if(musterija == null) {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja musterije sa tim korisnickim imenom", "Greska", JOptionPane.WARNING_MESSAGE);
					}else {
						
						MusterijaDodavanjeIzmena izmena = new MusterijaDodavanjeIzmena(administrator, musterija);
						izmena.setVisible(true);
					}
				}
			}
		});
	}
}
