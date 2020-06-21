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

import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.Serviser;

public class ZahteviZaServis extends JFrame {
	private JTable tabelaServisa;
	private DefaultTableModel tableModel;
	private JToolBar mainToolbar = new JToolBar();
	private JButton prihvatiServis = new JButton("Prihvati servis");
	
	public ZahteviZaServis(Administrator admin) {
		setTitle("Zahtevi za servise");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(admin);
		initActions(admin);
		setSize(800,300);
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Administrator admin) {	

		mainToolbar.add(prihvatiServis);
		add(mainToolbar, BorderLayout.NORTH);
		String[] zaglavlja = new String[] {"ID servisa", "ID automobila", "Opis"};
		ArrayList<ServisAutomobila> zahtevi = new ArrayList<ServisAutomobila>();
		for(ServisAutomobila servis : UcitavanjePodataka.getNeobrisaniServisi()) {
			if(servis.getServiser() == null) {
				zahtevi.add(servis);
			}
		}
		Object[][] sadrzaj = new Object[zahtevi.size()][zaglavlja.length];
		for(int i=0; i<zahtevi.size();i++) {
			ServisAutomobila servis = zahtevi.get(i);
			sadrzaj[i][0] = servis.getId();
			sadrzaj[i][1] = servis.getAutomobilId();
			sadrzaj[i][2] = servis.getOpis();
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
		prihvatiServis.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = tabelaServisa.getSelectedRow();
				//ako je minus 1 znaci da nijedan nije izabran red u tabeli, uvek je takva provera
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Morate odabrati red u tabeli.", "Greska", JOptionPane.WARNING_MESSAGE);
				}else { 
					String ServisId = tableModel.getValueAt(red, 0).toString();
					ServisAutomobila servis = Povezivanje.getServisPoId(ServisId);
					DopuniServis dopuni = new DopuniServis(admin, servis);
					dopuni.setVisible(true);
				}	
				
			}
		});
	}
}
