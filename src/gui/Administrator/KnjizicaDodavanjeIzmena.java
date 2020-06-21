package gui.Administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import IO.DeoIO;
import IO.ServisnaKnjizicaIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Automobil;
import entiteti.Deo;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.Serviser;
import entiteti.ServisnaKnjizica;
import net.miginfocom.swing.MigLayout;

public class KnjizicaDodavanjeIzmena extends JFrame {
	private JLabel lblId = new JLabel("ID knjizice: ");
	private JTextField txtId = new JTextField(20);
	private JLabel lblAutomobil = new JLabel("Automobil: ");
	private JComboBox<String> automobiliId = new JComboBox<String>();
	
	private JLabel sviServisi = new JLabel("Lista mogucih servisa: " + Povezivanje.getServisiId());
	private JLabel lblServis = new JLabel("Servisi ID(razdvojeni zarezom):");
	private JTextField txtServisi = new JTextField(20);
	private JButton btnPotvrdi = new JButton("Potrvdi");
	private JButton btnOtkazi = new JButton("Otkazi");
	
	public KnjizicaDodavanjeIzmena(Administrator administrator, ServisnaKnjizica knjizica) {
		if(knjizica == null) {
			setTitle("Dodavanje knjizice");
		}else {
			setTitle("Izmena podataka: " + knjizica.getId());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initGUI(administrator,knjizica);
		initActions(administrator, knjizica);
		setResizable(false);
		pack();
	}
	
	private void initGUI(Administrator administrator, ServisnaKnjizica knjizica) {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][]");
		setLayout(layout);
		
		if(knjizica != null) {
			popuniPolja(knjizica);
		}
		
		add(lblId);
		add(txtId);
		add(lblAutomobil);
		
		for(Automobil automobil : UcitavanjePodataka.getNeobrisaniAutomobili()) {
			automobiliId.addItem(automobil.getId());
		}
		add(automobiliId);
		add(sviServisi, "span 2");
		add(lblServis);
		add(txtServisi);
		add(new JLabel());
		add(btnPotvrdi, "split 2");
		add(btnOtkazi);
	}
	
	private void initActions(Administrator administrator, ServisnaKnjizica knjizica) {
		btnOtkazi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				KnjizicaDodavanjeIzmena.this.dispose();
				KnjizicaDodavanjeIzmena.this.setVisible(false);
			}
		});
		
		btnPotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ok = validacija(administrator);
				if(ok == true) {
					String id = txtId.getText().trim();
					String automobilId = (String)automobiliId.getSelectedItem();
					Automobil automobil = Povezivanje.getAutomobilPoId(automobilId);
					String listaServisaStr = txtServisi.getText().trim();
					String[] listaServisa = listaServisaStr.split(",");
					ArrayList<ServisAutomobila> listaServisaObj = Povezivanje.getServisPoId(listaServisa);
					
					if(knjizica == null) {//dodavanje
						
						if(Povezivanje.getKnjiziceId().contains(txtId.getText().trim())) {
							JOptionPane.showMessageDialog(null, "Knjizica sa tim ID vec postoji", "Greska", JOptionPane.WARNING_MESSAGE);
							ok = false;
						}
						else {
						
						ServisnaKnjizica knjizica = new ServisnaKnjizica(id, automobil, listaServisaObj, false);
						UcitavanjePodataka.addKnjizica(knjizica);
						ServisnaKnjizicaIO.ServisnaKnjizicaWriter(UcitavanjePodataka.getKnjizice());
						KnjizicaDodavanjeIzmena.this.dispose();
						KnjizicaDodavanjeIzmena.this.setVisible(false);
						}
					}
						else { //izmena
							UcitavanjePodataka.changeKnjizica(id, automobil, listaServisaObj, false);
							ServisnaKnjizicaIO.ServisnaKnjizicaWriter(UcitavanjePodataka.getKnjizice());
							KnjizicaDodavanjeIzmena.this.dispose();
							KnjizicaDodavanjeIzmena.this.setVisible(false);
						}
			}
			}
		});
	}
	
	private void popuniPolja(ServisnaKnjizica knjizica) {
		txtId.setText(knjizica.getId());
		automobiliId.setSelectedItem(knjizica.getAutomobilId());
		String servisiStr = "";
		ArrayList<String> servisiId = knjizica.getListaServisaId();
		for(String i : servisiId) {
			servisiStr += i + ",";
		}
		String servisiID = servisiStr.substring(0, servisiStr.length() -1);
		txtServisi.setText(servisiID);
	}
	
	private boolean validacija(Administrator administrator) {
		boolean ok = true;
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		
		
		if(txtId.getText().trim().equals("")) {
			poruka += "- Unesite id\n";
			ok = false;
		}
		
		if(Povezivanje.KnjizicaAutomobilVecPostoji((String)automobiliId.getSelectedItem())) {
			poruka += "- Izabrani automobil vec ima servisnu knjizicu";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);

		}
		
		return ok;
		
	}
	
}
