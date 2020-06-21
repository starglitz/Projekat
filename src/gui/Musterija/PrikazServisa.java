package gui.Musterija;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import entiteti.Automobil;
import entiteti.Musterija;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;

public class PrikazServisa extends JFrame {
	private JTable tabelaServisa;
	private DefaultTableModel tableModel;
	
	public PrikazServisa(Musterija musterija) {
		setTitle("Prikaz servisa musterije: " + musterija.getIme() +" "+ musterija.getPrezime());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(musterija);
//		initActions(musterija);
		setSize(900,300);
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Musterija musterija) {
		String[] zaglavlja = new String[] {"ID servisa", "ID automobila", "Termin", "Opis", "Lista delova", "Status", "Troskovi"};
		ArrayList<Automobil> automobiliMusterije = Povezivanje.getAutomobiliMusterije(musterija.getId());
		ArrayList<ServisAutomobila> servisiMusterije = Povezivanje.getServisiMusterije(automobiliMusterije);
		
		ArrayList<ServisAutomobila> potpuniServisi = new ArrayList<ServisAutomobila>();
		for(ServisAutomobila servis : servisiMusterije) {
			if(servis.getServiser() != null) {
				potpuniServisi.add(servis);
			}
		}
		Object[][] sadrzaj = new Object[potpuniServisi.size()][zaglavlja.length];
		
		for(int i=0; i<potpuniServisi.size();i++) {
			ServisAutomobila servis = potpuniServisi.get(i);
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
}
