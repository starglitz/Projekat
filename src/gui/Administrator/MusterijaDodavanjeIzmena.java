package gui.Administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import IO.KorisnikIO;
import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Musterija;
import entiteti.Povezivanje;
import enumeracije.Pol;
import gui.Login;
import gui.Serviser.KreiranjeServisa;
import net.miginfocom.swing.MigLayout;

public class MusterijaDodavanjeIzmena extends JFrame {
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
	private JLabel lblBrBodova = new JLabel("Broj bodova: ");
	private JTextField txtBrBodova = new JTextField(20);
	
	private JButton btnPotvrdi = new JButton("Potvrdi");
	private JButton btnOtkazi = new JButton("Otkazi");
			
	public MusterijaDodavanjeIzmena(Administrator administrator, Musterija musterija) {
		if(musterija == null) {
			setTitle("Dodavanje musterije");
		}else {
			setTitle("Izmena podataka: " + musterija.getKorisnickoIme());
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		initGUI(administrator,musterija);
		initActions(administrator, musterija);
		setResizable(false);
		pack();
	}
	
	private void initGUI(Administrator administrator, Musterija musterija) {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[][][][][][]");
		setLayout(layout);
		
		if(musterija != null) {
			popuniPolja(musterija);
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
		add(lblBrBodova);
		add(txtBrBodova);
		add(new JLabel());
		add(btnPotvrdi, "split 2");
		add(btnOtkazi);
	}
	
	private void initActions(Administrator administrator, Musterija musterija) {
		btnOtkazi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MusterijaDodavanjeIzmena.this.dispose();
				MusterijaDodavanjeIzmena.this.setVisible(false);
				
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
					int brBodova = Integer.parseInt(txtBrBodova.getText().trim());
					
					if(musterija == null) {//dodavanje
						
						if(Povezivanje.getKorisniciId().contains(id)) {
							JOptionPane.showMessageDialog(null, "Korisnik sa tim ID vec postoji!", "Greska pri pravljenju korisnika", JOptionPane.WARNING_MESSAGE);
						}
						else {
						
						Musterija nova = new Musterija(id, ime, prezime, pol, adresa, telefon, korisnickoIme, lozinka, jmbg, brBodova, false);
						UcitavanjePodataka.addKorisnik(nova);
						KorisnikIO.KorisniciWriter(UcitavanjePodataka.getKorisnici());
						MusterijaDodavanjeIzmena.this.dispose();
						MusterijaDodavanjeIzmena.this.setVisible(false);
						}
						}
					
					else { //izmena
						UcitavanjePodataka.changeMusterija(id, ime, prezime, pol, adresa, telefon, korisnickoIme, lozinka, jmbg, brBodova, false);
						KorisnikIO.KorisniciWriter(UcitavanjePodataka.getKorisnici());
						MusterijaDodavanjeIzmena.this.dispose();
						MusterijaDodavanjeIzmena.this.setVisible(false);
					}
					
				}
				
			}
		});
	}
	
	private void popuniPolja(Musterija musterija) {
		txtId.setText(musterija.getId());
		txtId.setEditable(false);
		txtIme.setText(musterija.getIme());
		txtPrezime.setText(musterija.getPrezime());
		cbPol.setSelectedItem(musterija.getPol());
		txtAdresa.setText(musterija.getAdresa());
		txtTelefon.setText(musterija.getBrTelefona());
		txtKorisnickoIme.setText(musterija.getKorisnickoIme());
		txtLozinka.setText(musterija.getLozinka());
		txtJmbg.setText(musterija.getJmbg());
		String brBodovaStr = Integer.toString(musterija.getBrBodova()); 
		txtBrBodova.setText(brBodovaStr);
		
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
		if(txtBrBodova.getText().trim().equals("")) {
			poruka += "- Unesite broj bodova\n";
			ok = false;
		}
		
		try {
			Integer.parseInt(txtBrBodova.getText().trim());
		}
		catch(NumberFormatException e) {
			ok = false;
			poruka += "- Bodovi moraju biti celi broj\n"; 
		}
		if(isNumeric(txtBrBodova.getText().trim()) == true) {
			if(Integer.parseInt(txtBrBodova.getText().trim()) > 10) {
				ok = false;
				poruka += "- Najvisi broj bodova je 10\n";
			}
		}
	
		if(ok == false) {
			JOptionPane.showMessageDialog(null, poruka, "Neispravni podaci", JOptionPane.WARNING_MESSAGE);
		}
		
		return ok;
	}
		
	public static boolean isNumeric(String brBodova) { 
		  try {  
		    Integer.parseInt(brBodova);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
}
