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

import IO.DeoIO;
import IO.KorisnikIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Deo;
import entiteti.Musterija;
import entiteti.Povezivanje;
import enumeracije.Marka;
import enumeracije.Model;

public class PrikazDelova extends JFrame {

	private JTable tabelaDelova;
	private DefaultTableModel tableModel;
	private JToolBar mainToolbar = new JToolBar();
	private JButton btnDodaj = new JButton("Dodaj novi deo");
	private JButton btnIzmeni = new JButton("Izmeni deo");
	private JButton btnIzbrisi = new JButton("Obrisi deo");
	private JButton btnKreirajSimetricni = new JButton("Kreiraj simetricni deo");
	
	public PrikazDelova(Administrator administrator) {
		setTitle("Prikaz delova");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(administrator);
		initActions(administrator);
		setSize(600,400);
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Administrator administrator) {
		mainToolbar.add(btnDodaj);
		mainToolbar.add(btnIzmeni);
		mainToolbar.add(btnIzbrisi);
		mainToolbar.add(btnKreirajSimetricni);
		add(mainToolbar, BorderLayout.NORTH);
	
		String[] zaglavlja = new String[] {"ID" ,"Marka", "Model", "Naziv", "Cena"};
		Object[][] sadrzaj = new Object[UcitavanjePodataka.getNeobrisaniDelovi().size()][zaglavlja.length];
		
		for(int i=0; i<UcitavanjePodataka.getNeobrisaniDelovi().size(); i++) {
			Deo deo = UcitavanjePodataka.getNeobrisaniDelovi().get(i);
			sadrzaj[i][0] = deo.getId();
			sadrzaj[i][1] = deo.getMarka();
			sadrzaj[i][2] = deo.getModel();
			sadrzaj[i][3] = deo.getNaziv();
			sadrzaj[i][4] = deo.getCena();
		}
		tableModel = new DefaultTableModel(sadrzaj, zaglavlja);
		tabelaDelova = new JTable(tableModel);
		
		tabelaDelova.setRowSelectionAllowed(true);
		tabelaDelova.setColumnSelectionAllowed(false);
		tabelaDelova.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaDelova.setDefaultEditor(Object.class, null);
		tabelaDelova.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(tabelaDelova);
		add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public void initActions(Administrator administrator) {
		btnIzbrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaDelova.getSelectedRow();
				//ako je minus 1 znaci da nijedan nije izabran red u tabeli, uvek je takva provera
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String deoId = tableModel.getValueAt(red, 0).toString();
					Deo deo = Povezivanje.getJedanDeoPoId(deoId);
					int izbor = JOptionPane.showConfirmDialog(null, 
							"Da li ste sigurni da zelite da obrisete deo?", 
							 " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
					if(izbor == JOptionPane.YES_OPTION) {
						
						UcitavanjePodataka.deleteDeo(deo);
						tableModel.removeRow(red);
						DeoIO.DeoWriter(UcitavanjePodataka.getDelovi());
					}
						
				}	
			}
		});
		
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DeoDodavanjeIzmena dodavanje = new DeoDodavanjeIzmena(administrator, null);
				dodavanje.setVisible(true);
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaDelova.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					
					String deoId = tableModel.getValueAt(red, 0).toString();
					Deo deo = Povezivanje.getJedanDeoPoId(deoId);
					if(deo == null) {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja musterije sa tim korisnickim imenom", "Greska", JOptionPane.WARNING_MESSAGE);
					}else {
						
						DeoDodavanjeIzmena izmena = new DeoDodavanjeIzmena(administrator, deo);
						izmena.setVisible(true);
					}
				}
			}
		});
		
		
		btnKreirajSimetricni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaDelova.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String deoId = tableModel.getValueAt(red, 0).toString();
					Deo deo = Povezivanje.getJedanDeoPoId(deoId);
					if(deo.getNaziv().contains("strana") == false) {
						JOptionPane.showMessageDialog(null, "Ovaj deo nema levu/desnu stranu.", "Greska", JOptionPane.WARNING_MESSAGE);
					}
					else {
						kreirajSimetricniDeo(deo);
						JOptionPane.showMessageDialog(null, "Uspesno ste kreirali simetricni deo!", "Greska",JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
			
	}
	
	
	private void kreirajSimetricniDeo(Deo deo) {
		int random = (int )(Math.random() * 999999 + 100000);
		String deoId = Integer.toString(random);
		if(deo.getNaziv().contains("Desna")) {
			String leva = deo.getNaziv().replace("Desna", "Leva");
			Deo deoLevi = new Deo(deoId, deo.getMarka(), deo.getModel(), leva, deo.getCena(), deo.isObrisan());
			UcitavanjePodataka.addDeo(deoLevi);
			
		}
		if(deo.getNaziv().contains("Leva")) {
			String desna = deo.getNaziv().replace("Leva", "Desna");
			Deo deoDesni = new Deo(deoId, deo.getMarka(), deo.getModel(), desna, deo.getCena(), deo.isObrisan());
			UcitavanjePodataka.addDeo(deoDesni);
		}
		DeoIO.DeoWriter(UcitavanjePodataka.getDelovi());
	}
}


