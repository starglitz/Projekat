package gui.Administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import IO.ServisAutomobilaIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Automobil;
import entiteti.Deo;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.Serviser;
import enumeracije.StatusServisa;
import gui.Serviser.IzmenaServisa;
import gui.Serviser.KreiranjeServisa;
import net.miginfocom.swing.MigLayout;

public class ServisDodavanjeIzmena extends JFrame{
	private JLabel idServisa = new JLabel("ID: ");
	private JTextField txtIdServisa = new JTextField(20);
	
	private JComboBox<String> cbAutomobili = new JComboBox<String>();
	private JComboBox<String> cbServiseri = new JComboBox<String>();

	private JLabel idAutomobila = new JLabel("ID automobila: ");
	private JLabel idServisera = new JLabel("ID servisera: ");
	private JLabel termin = new JLabel("Termin(u formatu dd.mm.yyyy): ");
	private JTextField txtTermin = new JTextField(20);
	private JLabel opis = new JLabel("Opis: ");
	private JTextField txtOpis = new JTextField(20);
	private JLabel listaDelova = new JLabel("Lista ID delova(razdvojenih zarezom): ");
	private JTextField txtListaDelova = new JTextField(20);
	
	private JLabel lblTroskovi = new JLabel("Cena: ");
	private JTextField txtTroskovi = new JTextField(20);
	
	private JButton btnOK = new JButton("Potvrdi");
	private JButton btnCancel = new JButton("Otkazi");
	
	public ServisDodavanjeIzmena(Administrator admin, ServisAutomobila servis) {
		if(servis == null) {
			setTitle("Dodavanje servisa");
		}else {
			setTitle("Izmena podataka: " + servis.getId());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(admin,servis);
		initActions(admin,servis);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Administrator admin, ServisAutomobila servis) {

		ArrayList<Automobil> automobili = UcitavanjePodataka.getNeobrisaniAutomobili();
		for(Automobil automobil : automobili) {
			cbAutomobili.addItem(automobil.getId());
		}
		ArrayList<Serviser> serviseri = UcitavanjePodataka.getNeobrisaniServiseri();
		for(Serviser serviser : serviseri) {
			cbServiseri.addItem(serviser.getId());
		}
		
		String deloviStr = "";
		ArrayList<Deo> delovi = UcitavanjePodataka.getNeobrisaniDelovi();
		for(Deo deo : delovi) {
			deloviStr += deo.getId() + ",";
		}
		String deloviD = deloviStr.substring(0, deloviStr.length() - 1);
		String porukaZaDelove = "Dostupni delovi: " + deloviD;

		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][center][][]");
		setLayout(layout);
		
		if(servis != null) {
			popuniPolja(servis);
		}
		
		add(idServisa);
		add(txtIdServisa);  
		add(idAutomobila);
		add(cbAutomobili); 
		add(idServisera);
		add(cbServiseri);
		add(termin);
		add(txtTermin);
		add(opis);
		add(txtOpis);
		add(new JLabel(porukaZaDelove), "span 2");
		add(listaDelova);
		add(txtListaDelova);
		add(lblTroskovi);
		add(txtTroskovi);
		add(new JLabel());
		add(btnOK, "split 2");
		add(btnCancel);
	}
	
	
	public void initActions(Administrator admin, ServisAutomobila servis) {
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ServisDodavanjeIzmena.this.dispose();
				ServisDodavanjeIzmena.this.setVisible(false);
			}
		});
		
		
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ok = validacija();
				if(ok == true) {
					String servisID = txtIdServisa.getText().trim();
					String automobilID = (String)cbAutomobili.getSelectedItem();
					String serviserID = (String)cbServiseri.getSelectedItem();
					String termin = txtTermin.getText().trim();
					String opis = txtOpis.getText().trim();
					String listaDelovaStr = txtListaDelova.getText().trim();
					GregorianCalendar terminGregorian = ServisAutomobila.StringToGregorian(termin);
					Automobil automobil = Povezivanje.getAutomobilPoId(automobilID);
					Serviser serviser = Povezivanje.getServiserPoId(serviserID);
					String[] listaDelova = listaDelovaStr.split(",");
					ArrayList<Deo> listaDeloviObj = Povezivanje.getDeoPoId(listaDelova);
					
					String troskoviStr = txtTroskovi.getText().trim();
					int troskovi = Integer.parseInt(troskoviStr);
					
					if(servis == null) {//dodavanje
						
						if(Povezivanje.getServisiId().contains(servisID)) {
							JOptionPane.showMessageDialog(null, "Servis sa tim ID vec postoji!", "Greska pri pravljenju servisa", JOptionPane.WARNING_MESSAGE);
						}
						else {
						
						ServisAutomobila noviServis = new ServisAutomobila(servisID, automobil, opis, StatusServisa.ZAKAZAN, false, serviser, terminGregorian, listaDeloviObj, troskovi);
						UcitavanjePodataka.addServis(noviServis);
						ServisAutomobilaIO.ServisAutomobilaWriter(UcitavanjePodataka.getServisi());
						ServisDodavanjeIzmena.this.dispose();
						ServisDodavanjeIzmena.this.setVisible(false);
						}
				}
					else { //izmena
						UcitavanjePodataka.changeServis(servisID, automobil, opis, StatusServisa.ZAKAZAN, false, serviser, terminGregorian, listaDeloviObj, troskovi);
						ServisAutomobilaIO.ServisAutomobilaWriter(UcitavanjePodataka.getServisi());
						ServisDodavanjeIzmena.this.dispose();
						ServisDodavanjeIzmena.this.setVisible(false);
					}
				}
				}
				});	
		
			}
	
	private boolean validacija() {
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		boolean ok = true;
		String listaDelovaStr = txtListaDelova.getText().trim();
		String[] listaDelova = listaDelovaStr.split(",");
		ArrayList<Deo> listaDelovi = Povezivanje.getDeoPoId(listaDelova);
		if(listaDelovi.isEmpty()) {
			poruka += "- Unesite ispravne ID delova\n";
			ok = false;
		}
		
		String automobilID = (String)cbAutomobili.getSelectedItem();
		Automobil automobil = Povezivanje.getAutomobilPoId(automobilID);
		
		for(Deo deo : listaDelovi) {
			if((deo.getModel() == automobil.getModel() && deo.getMarka() == automobil.getMarka()) == false) {
				ok = false;
				poruka += "- Uneti delovi nisu namenjeni za model i marku vaseg auta\n";
	
		}
		}
		
		if(txtIdServisa.getText().trim().equals("")) {
			poruka += "- Unesite id\n";
			ok = false;
		}
		
		if(txtOpis.getText().trim().equals("")) {
			poruka += "- Unesite opis\n";
			ok = false;
		}
		
		if(TerminIsValid(txtTermin.getText().trim()) == false) {
			poruka += "- Termin mora biti u formatu dd.mm.yyyy";
			ok = false;
		}
	
		
		try {
			Integer.parseInt(txtTroskovi.getText().trim());
		}
		catch(NumberFormatException e) {
			poruka += "- Troskovi moraju biti celi broj/n";
			ok = false;
		}
		
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
	}
	
	private void popuniPolja(ServisAutomobila servis) {
		txtIdServisa.setText(servis.getId());
		cbAutomobili.setSelectedItem(servis.getAutomobilId());
		cbServiseri.setSelectedItem(servis.getServiserId());
		txtTermin.setText(servis.getTerminSimpleDate());
		txtOpis.setText(servis.getOpis());
		
		String deloviStr = "";
		ArrayList<String> deloviId = servis.getListaIdDelova();
		for(String i : deloviId) {
			deloviStr += i + ",";
		}
		String deloviD = deloviStr.substring(0, deloviStr.length() - 1);
		txtListaDelova.setText(deloviD);
		
		txtTroskovi.setText(Integer.toString(servis.getTroskovi()));
		
}
	private boolean TerminIsValid(String termin) {
		SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
		 format.setLenient(false);
		 try {
	            format.parse(termin.trim());
	        } catch (ParseException pe) {
	            return false;
	        }
	        return true;
	}
	
}
