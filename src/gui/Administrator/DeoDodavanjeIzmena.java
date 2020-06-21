package gui.Administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import IO.DeoIO;
import IO.KorisnikIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Deo;
import entiteti.Musterija;
import entiteti.Povezivanje;
import enumeracije.Marka;
import enumeracije.Model;
import enumeracije.Specijalizacija;
import net.miginfocom.swing.MigLayout;

public class DeoDodavanjeIzmena extends JFrame {
	private JLabel lblId = new JLabel("ID dela: ");
	private JTextField txtId = new JTextField(20);
	private JLabel lblMarka = new JLabel("Marka: ");
	private JComboBox<Marka> cbMarka = new JComboBox<Marka>(Marka.values());
	private JLabel lblModel = new JLabel("Model: ");
	private JComboBox<Model> cbModel = new JComboBox<Model>(Model.values());
	private JLabel lblNaziv = new JLabel("Naziv: ");
	private JTextField txtNaziv = new JTextField(20);
	private JLabel lblCena = new JLabel("Cena: ");
	private JTextField txtCena = new JTextField(20);
	
	private JButton btnPotvrdi = new JButton("Potrvdi");
	private JButton btnOtkazi = new JButton("Otkazi");
	
	public DeoDodavanjeIzmena(Administrator administrator, Deo deo) {
		if(deo == null) {
			setTitle("Dodavanje dela");
		}else {
			setTitle("Izmena podataka: " + deo.getNaziv());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(administrator,deo);
		initActions(administrator, deo);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}
	
	private void initGUI(Administrator administrator, Deo deo) {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][]");
		setLayout(layout);
		
		if(deo != null) {
			popuniPolja(deo);
		}
		
		add(lblId);
		add(txtId);
		add(lblMarka);
		add(cbMarka);
		add(lblModel);
		add(cbModel);
		add(lblNaziv);
		add(txtNaziv);
		add(lblCena);
		add(txtCena);
		add(new JLabel());
		add(btnPotvrdi, "split 2");
		add(btnOtkazi);
	}
	
	private void initActions(Administrator administrator, Deo deo) {
		btnOtkazi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DeoDodavanjeIzmena.this.dispose();
				DeoDodavanjeIzmena.this.setVisible(false);
				
			}
		});
		
		btnPotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ok = validacija(administrator);
				if(ok == true) {
					String id = txtId.getText().trim();
					
					
					Marka marka = (Marka)cbMarka.getSelectedItem();
					Model model = (Model)cbModel.getSelectedItem();
					String naziv = txtNaziv.getText().trim();
					int cena = Integer.parseInt(txtCena.getText().trim());
					
					if(deo == null) {//dodavanje
						if(Povezivanje.getDeloviId().contains(id)) {
							JOptionPane.showMessageDialog(null, "Deo sa tim ID vec postoji!", "Greska pri pravljenju dela", JOptionPane.WARNING_MESSAGE);
						}
						else {
						Deo novi = new Deo(id, marka, model, naziv, cena, false);
						UcitavanjePodataka.addDeo(novi);
						DeoIO.DeoWriter(UcitavanjePodataka.getDelovi());
						DeoDodavanjeIzmena.this.dispose();
						DeoDodavanjeIzmena.this.setVisible(false);
						}

					}
					else { //izmena
						UcitavanjePodataka.changeDeo(id, marka, model, naziv, cena, false);
						DeoIO.DeoWriter(UcitavanjePodataka.getDelovi());
						DeoDodavanjeIzmena.this.dispose();
						DeoDodavanjeIzmena.this.setVisible(false);
					}
				}
				
			}
		});
	}
	
	private void popuniPolja(Deo deo) {
		txtId.setText(deo.getId());
		cbMarka.setSelectedItem(deo.getMarka());
		cbModel.setSelectedItem(deo.getModel());
		txtNaziv.setText(deo.getNaziv());
		String cenaStr = Integer.toString(deo.getCena());
		txtCena.setText(cenaStr);
	}
	
	private boolean validacija(Administrator administrator) {
		boolean ok = true;
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		
		
		if(txtId.getText().trim().equals("")) {
			poruka += "- Unesite id\n";
			ok = false;
		}
		
		if(txtNaziv.getText().equals("")) {
			poruka += "- Unesite naziv\n";
			ok = false;
		}
		try {
			Integer.parseInt(txtCena.getText().trim());
		}
		catch(NumberFormatException e) {
			ok = false;
			poruka += "- Cena mora biti celi broj\n"; 
		}
		
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		
		return ok;
	}
}
