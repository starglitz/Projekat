package gui.Administrator;

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

import IO.KorisnikIO;
import IO.ServisnaKnjizicaIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Automobil;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.Serviser;
import entiteti.ServisnaKnjizica;

public class PrikazKnjizica extends JFrame {
	private JTable tabelaKnjizica;
	private DefaultTableModel tableModel;
	private JButton btnDodaj = new JButton("Dodaj novu knjizicu");
	private JButton btnIzmeni = new JButton("Izmeni knjizicu");
	private JButton btnObrisi = new JButton("Obrisi knjizicu");
	private JToolBar mainToolbar = new JToolBar();
	
	public PrikazKnjizica(Administrator administrator) {
		setTitle("Prikaz servisnih knjizica");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(administrator);
		initActions(administrator);
		setSize(400,400);
		setLocationRelativeTo(null);
	}
	public void initGUI(Administrator administrator) {
		mainToolbar.add(btnDodaj);
		mainToolbar.add(btnIzmeni);
		mainToolbar.add(btnObrisi);
		
		add(mainToolbar, BorderLayout.NORTH);
		String[] zaglavlja = new String[] {"ID knjizice", "ID automobila", "Lista servisa"};
		Object[][] sadrzaj = new Object[UcitavanjePodataka.getNeobrisaneKnjizice().size()][zaglavlja.length];
		
		for(int i=0; i<UcitavanjePodataka.getNeobrisaneKnjizice().size(); i++) {
			
			ServisnaKnjizica knjizica = UcitavanjePodataka.getNeobrisaneKnjizice().get(i);
			sadrzaj[i][0] = knjizica.getId();
			sadrzaj[i][1] = knjizica.getAutomobilId();
			sadrzaj[i][2] = knjizica.getListaServisaId();
		}
			tableModel = new DefaultTableModel(sadrzaj, zaglavlja);
			tabelaKnjizica = new JTable(tableModel);
			
			tabelaKnjizica.setRowSelectionAllowed(true);
			tabelaKnjizica.setColumnSelectionAllowed(false);
			tabelaKnjizica.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabelaKnjizica.setDefaultEditor(Object.class, null);
			tabelaKnjizica.getTableHeader().setReorderingAllowed(false);
			
			JScrollPane scrollPane = new JScrollPane(tabelaKnjizica);
			add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public void initActions(Administrator administrator) {
		btnObrisi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaKnjizica.getSelectedRow();
				//ako je minus 1 znaci da nijedan nije izabran red u tabeli, uvek je takva provera
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String knjizicaId = tableModel.getValueAt(red, 0).toString();
					ServisnaKnjizica knjizica = Povezivanje.getKnjizicaPoId(knjizicaId);
					int izbor = JOptionPane.showConfirmDialog(null, 
							"Da li ste sigurni da zelite da obrisete knjizicu?", 
							 " - Potvrda brisanja", JOptionPane.YES_NO_OPTION);
					if(izbor == JOptionPane.YES_OPTION) {
						UcitavanjePodataka.deleteKnjizica(knjizica);;
						tableModel.removeRow(red);
						ServisnaKnjizicaIO.ServisnaKnjizicaWriter(UcitavanjePodataka.getKnjizice());
					}
				}	
			}
		});
		
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				KnjizicaDodavanjeIzmena dodavanje = new KnjizicaDodavanjeIzmena(administrator, null);
				dodavanje.setVisible(true);
			}
		});
		
		btnIzmeni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaKnjizica.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else {
					String knjizicaId = tableModel.getValueAt(red, 0).toString();
					ServisnaKnjizica knjizica = Povezivanje.getKnjizicaPoId(knjizicaId);
					if(knjizica == null) {
						JOptionPane.showMessageDialog(null, "Greska prilikom pronalazenja knjizice", "Greska", JOptionPane.WARNING_MESSAGE);
					}else {
						
						KnjizicaDodavanjeIzmena izmena = new KnjizicaDodavanjeIzmena(administrator, knjizica);
						izmena.setVisible(true);
					}
				}
			}
		});
		
	}
	
}
