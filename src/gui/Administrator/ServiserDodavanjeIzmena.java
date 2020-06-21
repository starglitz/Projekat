package gui.Administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import IO.KorisnikIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Musterija;
import entiteti.Povezivanje;
import entiteti.Serviser;
import enumeracije.Pol;
import enumeracije.Specijalizacija;
import net.miginfocom.swing.MigLayout;

public class ServiserDodavanjeIzmena extends JFrame {
	private JLabel lblId = new JLabel("ID: ");
	private JTextField txtId = new JTextField(20);
	private JLabel lblIme = new JLabel("Ime: ");
	private JTextField txtIme = new JTextField(20);
	private JLabel lblPrezime = new JLabel("Prezime: ");
	private JTextField txtPrezime = new JTextField(20);
	private JLabel lblPol = new JLabel("Pol: ");
	private JComboBox<Pol> cbPol = new JComboBox<Pol>(Pol.values()); 
	private JLabel lblAdresa = new JLabel("Adresa: ");
	private JTextField txtAdresa = new JTextField(20);
	private JLabel lblTelefon = new JLabel("Broj telefona: ");
	private JTextField txtTelefon = new JTextField(20);
	private JLabel lblKorisnickoIme = new JLabel("Korisnicko ime: ");
	private JTextField txtKorisnickoIme = new JTextField(20);
	private JLabel lblLozinka = new JLabel("Lozinka");
	private JTextField txtLozinka = new JTextField(20);
	private JLabel lblJmbg = new JLabel("JMBG: ");
	private JTextField txtJmbg = new JTextField(20);
	private JLabel lblPlata = new JLabel("Plata: ");
	private JLabel lblSpecijalizacija = new JLabel("Specijalizacija: ");
	private JTextField txtPlata = new JTextField(20);
	private JComboBox<Specijalizacija> cbSpecijalizacija = new JComboBox<Specijalizacija>(Specijalizacija.values());
	
	private JButton btnPotvrdi = new JButton("Potvrdi");
	private JButton btnOtkazi = new JButton("Otkazi");
	
	public ServiserDodavanjeIzmena(Administrator administrator, Serviser serviser) {
		if(serviser == null) {
			setTitle("Dodavanje servisera");
		}else {
			setTitle("Izmena podataka: " + serviser.getKorisnickoIme());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initGUI(administrator,serviser);
		initActions(administrator, serviser);
		setResizable(false);
		pack();
	}
	
	private void initGUI(Administrator administrator, Serviser serviser) {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][]");
		setLayout(layout);
		
		if(serviser != null) {
			popuniPolja(serviser);
		}
		
		add(lblId);
		add(txtId);
		add(lblIme);
		add(txtIme);
		add(lblPrezime);
		add(txtPrezime);
		add(lblPol);
		add(cbPol);
		add(lblAdresa);
		add(txtAdresa);
		add(lblTelefon);
		add(txtTelefon);
		add(lblKorisnickoIme);
		add(txtKorisnickoIme);
		add(lblLozinka);
		add(txtLozinka);
		add(lblJmbg);
		add(txtJmbg);
		add(lblPlata);
		add(txtPlata);
		add(lblSpecijalizacija);
		add(cbSpecijalizacija);
		add(new JLabel());
		add(btnPotvrdi, "split 2");
		add(btnOtkazi);
	}
	
	private void initActions(Administrator administrator, Serviser serviser) {
		btnOtkazi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ServiserDodavanjeIzmena.this.dispose();
				ServiserDodavanjeIzmena.this.setVisible(false);
				
			}
		});
		
		btnPotvrdi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ok = validacija(administrator);
				if(ok == true) {
					String id = txtId.getText().trim();
					String ime = txtIme.getText().trim();
					String prezime = txtPrezime.getText().trim();
					Pol pol = (Pol)cbPol.getSelectedItem();
					String adresa = txtAdresa.getText().trim();
					String telefon = txtTelefon.getText().trim();
					String korisnickoIme = txtKorisnickoIme.getText().trim();
					String lozinka = txtLozinka.getText().trim();
					String jmbg = txtJmbg.getText().trim();
					int plata = Integer.parseInt(txtPlata.getText().trim());
					Specijalizacija specijalizacija = (Specijalizacija)cbSpecijalizacija.getSelectedItem();
					
					if(serviser == null) {//dodavanje
						if(Povezivanje.getKorisniciId().contains(id)) {
							JOptionPane.showMessageDialog(null, "Korisnik sa tim ID vec postoji!", "Greska pri pravljenju korisnika", JOptionPane.WARNING_MESSAGE);
						}
						else {
						Serviser novi = new Serviser(id, ime, prezime, pol, adresa, telefon, korisnickoIme, lozinka, jmbg, plata, specijalizacija, false);
						UcitavanjePodataka.addKorisnik(novi);
						KorisnikIO.KorisniciWriter(UcitavanjePodataka.getKorisnici());
						ServiserDodavanjeIzmena.this.dispose();
						ServiserDodavanjeIzmena.this.setVisible(false);
						}
					}
					else { //izmena
						UcitavanjePodataka.changeServiser(id, ime, prezime, pol, adresa, telefon, korisnickoIme, lozinka, jmbg, plata, specijalizacija, false);
						KorisnikIO.KorisniciWriter(UcitavanjePodataka.getKorisnici());
						ServiserDodavanjeIzmena.this.dispose();
						ServiserDodavanjeIzmena.this.setVisible(false);
					}
					
				}
				
			}
		});
	}
	
	private void popuniPolja(Serviser serviser) {
		txtId.setText(serviser.getId());
		txtId.setEditable(false);
		txtIme.setText(serviser.getIme());
		txtPrezime.setText(serviser.getPrezime());
		cbPol.setSelectedItem(serviser.getPol());
		txtAdresa.setText(serviser.getAdresa());
		txtTelefon.setText(serviser.getBrTelefona());
		txtKorisnickoIme.setText(serviser.getKorisnickoIme());
		txtLozinka.setText(serviser.getLozinka());
		txtJmbg.setText(serviser.getJmbg());
		String plataStr = Integer.toString(serviser.getPlata());
		txtPlata.setText(plataStr);
		cbSpecijalizacija.setSelectedItem(serviser.getSpecijalizacija());
	}
	
	private boolean validacija(Administrator administrator) {
		boolean ok = true;
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		
		
		if(txtId.getText().trim().equals("")) {
			poruka += "- Unesite id\n";
			ok = false;
		}
		
		if(txtIme.getText().trim().equals("")) {
			poruka += "- Unesite ime\n";
			ok = false;
		}
		
		if(txtPrezime.getText().trim().equals("")) {
			poruka += "- Unesite prezime\n";
			ok = false;
		}
		if(txtAdresa.getText().trim().equals("")) {
			poruka += "- Unesite adresu\n";
			ok = false;
		}
		
		if(txtTelefon.getText().trim().equals("")) {
			poruka += "- Unesite broj telefona\n";
			ok = false;
		}
		
		if(txtKorisnickoIme.getText().trim().equals("")) {
			poruka += "- Unesite korisnicko ime\n";
			ok = false;
		}
		
		if(txtLozinka.getText().trim().equals("")) {
			poruka += "- Unesite lozinku\n";
			ok = false;
		}
		if(txtJmbg.getText().trim().equals("")) {
			poruka += "- Unesite jmbg\n";
			ok = false;
		}
		if(txtPlata.getText().trim().equals("")) {
			poruka += "- Unesite platu\n";
			ok = false;
		}
		
		try {
			Integer.parseInt(txtPlata.getText().trim());
		}
		catch(NumberFormatException e) {
			ok = false;
			poruka += "- Plata treba da bude celi broj\n"; 
		}
		
		
	
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);

		}
		
		return ok;
	}
}
