package gui.Serviser;


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
import entiteti.Automobil;
import entiteti.Deo;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import entiteti.Serviser;
import enumeracije.StatusServisa;
import net.miginfocom.swing.MigLayout;

public class KreiranjeServisa extends JFrame {
	
	private JLabel poruka = new JLabel("Unesite podatke o novom servisu: ");
	private JLabel idServisa = new JLabel("ID: ");
	private JTextField txtIdServisa = new JTextField(20);
	
	private JComboBox<String> cbAutomobili = new JComboBox<String>();

	private JLabel idAutomobila = new JLabel("ID automobila: ");
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
	
	public KreiranjeServisa(Serviser serviser) {
		setTitle("Kreiranje novog servisa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(serviser);
		initActions(serviser);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Serviser serviser) {

				ArrayList<Automobil> automobili = UcitavanjePodataka.getNeobrisaniAutomobili();
				for(Automobil automobil : automobili) {
					cbAutomobili.addItem(automobil.getId());
				}
				String deloviStr = "";
				ArrayList<Deo> delovi = UcitavanjePodataka.getNeobrisaniDelovi();
				for(Deo deo : delovi) {
					deloviStr += deo.getId() + ",";
				}
				String deloviD = deloviStr.substring(0, deloviStr.length() - 1);
				String porukaZaDelove = "Dostupni delovi: " + deloviD;

				MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][][]20[]");
				setLayout(layout);
				
				add(poruka, "wrap 2");
				add(idServisa);
				add(txtIdServisa);  
				add(idAutomobila);
				add(cbAutomobili); 
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
	
public void initActions(Serviser serviser) {
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				KreiranjeServisa.this.dispose();
				KreiranjeServisa.this.setVisible(false);
			}
		});
		
		
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ok = validacija(serviser);
				if(ok == true) {	
				String servisID = txtIdServisa.getText().trim();
				String automobilID = (String)cbAutomobili.getSelectedItem();
				String termin = txtTermin.getText().trim();
				String opis = txtOpis.getText().trim();
				String listaDelovaStr = txtListaDelova.getText().trim();
				GregorianCalendar terminGregorian = ServisAutomobila.StringToGregorian(termin);
				Automobil automobil = Povezivanje.getAutomobilPoId(automobilID);
				
			
				String[] listaDelova = listaDelovaStr.split(",");
				ArrayList<Deo> listaDeloviObj = Povezivanje.getDeoPoId(listaDelova);
				
				String troskoviStr = txtTroskovi.getText().trim();
				int troskovi = Integer.parseInt(troskoviStr);
				ServisAutomobila noviServis = new ServisAutomobila(servisID, automobil, opis, StatusServisa.ZAKAZAN, false, serviser, terminGregorian, listaDeloviObj, troskovi);
				UcitavanjePodataka.addServis(noviServis);

				ServisAutomobilaIO.ServisAutomobilaWriter(UcitavanjePodataka.getServisi());
				KreiranjeServisa.this.dispose();
				KreiranjeServisa.this.setVisible(false);
				}
			}
		});	
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
	

	private boolean validacija(Serviser serviser) {
		
		String listaDelovaStr = txtListaDelova.getText().trim();
		String[] listaDelova = listaDelovaStr.split(",");
		ArrayList<Deo> listaDelovi = Povezivanje.getDeoPoId(listaDelova);
		
		boolean ok = true;
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		
		String automobilID = (String)cbAutomobili.getSelectedItem();
		Automobil automobil = Povezivanje.getAutomobilPoId(automobilID);
		
		for(Deo deo : listaDelovi) {
			if((deo.getModel() == automobil.getModel() && deo.getMarka() == automobil.getMarka()) == false) {
				ok = false;
				poruka += "- Uneti delovi nisu namenjeni za model i marku vaseg auta\n";
	
		}
		}
		
		if(Povezivanje.getServisiId().contains(txtIdServisa.getText().trim())) {
			poruka += "- Servis sa tim ID vec postoji\n";
			ok = false;
		}
		
		if(txtIdServisa.getText().trim().equals("")) {
			poruka += "- Unesite ID servisa\n";
			ok = false;
		}
		if(TerminIsValid(txtTermin.getText().trim()) == false) {
			poruka += "- Termin mora biti u formatu dd.mm.yyyy";
			ok = false;
		}
		
		if(txtOpis.getText().trim().equals("")) {
			poruka += "- Unesite opis\n";
			ok = false;
		}
		if(txtListaDelova.getText().equals("")){
			poruka += "- Unesite delove\n";
			ok = false;
			}
		
		if(listaDelovi.isEmpty()) {
			poruka += "- Unesite ispravne ID delova\n";
			ok = false;
		}
		
		if(txtTroskovi.getText().trim().equals("")) {
			ok = false;
			poruka += "- Unesite ispravne podatke za troskove\n";
		}
		
		try {
			Integer.parseInt(txtTroskovi.getText().trim());
		}
		catch(NumberFormatException e) {
			ok = false;
			poruka += "- Troskovi moraju biti celi broj\n"; 
		}
		
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		return ok;
		}

}

