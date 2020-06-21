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

import IO.AutomobilIO;
import IO.KorisnikIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Automobil;
import entiteti.Musterija;
import entiteti.Povezivanje;
import enumeracije.Marka;
import enumeracije.Model;
import enumeracije.Pol;
import enumeracije.VrstaGoriva;
import net.miginfocom.swing.MigLayout;

public class AutomobilDodavanjeIzmena extends JFrame {

	private JLabel lblId = new JLabel("ID automobila: ");
	private JTextField txtId = new JTextField(20);
	private JLabel lblVlasnik = new JLabel("Vlasnik: ");
	private JComboBox<String> cbVlasnik = new JComboBox<String>();
	private JLabel lblMarka = new JLabel("Marka: ");
	private JComboBox<Marka> cbMarka = new JComboBox<Marka>(Marka.values());
	private JLabel lblModel = new JLabel("Model: ");
	private JComboBox<Model> cbModel = new JComboBox<Model>(Model.values());
	private JLabel lblGodProizvodnje = new JLabel("Godina proizvodnje: ");
	private JTextField txtGodProizvodnje = new JTextField(20);
	private JLabel lblZapreminaM = new JLabel("Zapremina motora: ");
	private JTextField txtZapreminaM = new JTextField(20);
	private JLabel lblSnagaM = new JLabel("Snaga motora: ");
	private JTextField txtSnagaM = new JTextField(20);
	private JLabel lblVrstaGoriva = new JLabel("Vrsta goriva: ");
	private JComboBox<VrstaGoriva> cbVrstaGoriva = new JComboBox<VrstaGoriva>(VrstaGoriva.values());
	private JButton btnPotvrdi = new JButton("Potrvdi");
	private JButton btnOtkazi = new JButton("Otkazi");
	
	
	
	
	public AutomobilDodavanjeIzmena(Administrator administrator, Automobil auto) {
		if(auto == null) {
			setTitle("Dodavanje automobila");
		}else {
			setTitle("Izmena podataka: " + auto.getId());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(administrator,auto);
		initActions(administrator,auto);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Administrator administrator, Automobil auto) {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][]");
		setLayout(layout);
		ArrayList<String> vlasniciId = getVlasniciId();
		for(String i : vlasniciId) {
			cbVlasnik.addItem(i);
		}
		if(auto != null) {
			popuniPolja(auto);
		}
		
		add(lblId);
		add(txtId);
		add(lblVlasnik);
		add(cbVlasnik);
		add(lblMarka);
		add(cbMarka);
		add(lblModel);
		add(cbModel);
		add(lblGodProizvodnje);
		add(txtGodProizvodnje);
		add(lblZapreminaM);
		add(txtZapreminaM);
		add(lblSnagaM);
		add(txtSnagaM);
		add(lblVrstaGoriva);
		add(cbVrstaGoriva);
		add(new JLabel());
		add(btnPotvrdi, "split 2");
		add(btnOtkazi);
	}
	
	public ArrayList<String> getVlasniciId() {
		ArrayList<String> vlasniciId = new ArrayList<String>();
		for(Musterija vlasnik : UcitavanjePodataka.getNeobrisaneMusterije()) {
			vlasniciId.add(vlasnik.getId());
		}
	return vlasniciId;
	}
	
	private void initActions(Administrator administrator, Automobil auto) {
		btnOtkazi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AutomobilDodavanjeIzmena.this.dispose();
				AutomobilDodavanjeIzmena.this.setVisible(false);
				
			}
		});
		
		btnPotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ok = validacija(administrator);
				if(ok == true) {
					String id = txtId.getText().trim();
					String vlasnikId = (String)cbVlasnik.getSelectedItem();
					Musterija vlasnik = Povezivanje.getMusterijaPoId(vlasnikId);
					Marka marka = (Marka)cbMarka.getSelectedItem();
					Model model = (Model)cbModel.getSelectedItem();
					int godinaProizvodnje = Integer.parseInt(txtGodProizvodnje.getText().trim());
					String zapreminaMotora = txtZapreminaM.getText().trim();
					String snagaMotora = txtSnagaM.getText().trim();
					VrstaGoriva vrstaGoriva = (VrstaGoriva)cbVrstaGoriva.getSelectedItem();
					
					if(auto == null) {//dodavanje
						if(Povezivanje.getAutomobiliId().contains(id)) {
							JOptionPane.showMessageDialog(null, "Automobil sa tim ID vec postoji!", "Greska pri pravljenju automobila", JOptionPane.WARNING_MESSAGE);
						}
						else {
						
						Automobil novi = new Automobil(id, vlasnik, marka, model, godinaProizvodnje, zapreminaMotora, snagaMotora, vrstaGoriva, false);
						UcitavanjePodataka.addAutomobil(novi);
						AutomobilIO.AutomobilWriter(UcitavanjePodataka.getAutomobili());
						AutomobilDodavanjeIzmena.this.dispose();
						AutomobilDodavanjeIzmena.this.setVisible(false);
						}
					}
					else { //izmena
						UcitavanjePodataka.changeAutomobil(id, vlasnik, marka, model, godinaProizvodnje, zapreminaMotora, snagaMotora, vrstaGoriva, false);
						AutomobilIO.AutomobilWriter(UcitavanjePodataka.getAutomobili());
						AutomobilDodavanjeIzmena.this.dispose();
						AutomobilDodavanjeIzmena.this.setVisible(false);
					}
	
				}
			}
		});
	
}
	private void popuniPolja(Automobil auto) {
		txtId.setText(auto.getId());
		cbVlasnik.setSelectedItem(auto.getVlasnikId());
		cbMarka.setSelectedItem(auto.getMarka());
		cbModel.setSelectedItem(auto.getModel());
		txtGodProizvodnje.setText(Integer.toString(auto.getGodinaProizvodnje()));
		txtZapreminaM.setText(auto.getZapreminaMotora());
		txtSnagaM.setText(auto.getSnagaMotora());
		cbVrstaGoriva.setSelectedItem(auto.getVrstaGoriva());
}
	
	private boolean validacija(Administrator administrator) {
		boolean ok = true;
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		
		if(txtId.getText().trim().equals("")) {
			poruka += "- Unesite id\n";
			ok = false;
		}
		if(txtGodProizvodnje.getText().trim().equals("")) {
			poruka += "- Unesite godinu proizvodnje\n";
			ok = false;
		}
		try {
			Integer.parseInt(txtGodProizvodnje.getText().trim());
		}
		catch(NumberFormatException e) {
			ok = false;
			poruka += "- Godina proizvodnje treba da bude celi broj\n"; 
		}
		if(txtZapreminaM.getText().trim().equals("")) {
			poruka += "- Unesite zapreminu motora\n";
			ok = false;
		}
		if(txtSnagaM.getText().trim().equals("")) {
			poruka += "- Unesite snagu motora\n";
			ok = false;
		}
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
		
	}
	
}
