package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOError;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Toolkit;

import IO.UcitavanjePodataka;
import entiteti.Administrator;
import entiteti.Korisnik;
import entiteti.Musterija;
import entiteti.Serviser;
import net.miginfocom.swing.MigLayout;

public class Login extends JFrame {
	
	private JLabel lblPoruka = new JLabel("Dobrodosli u Servis automobila! Molimo da se prijavite: ");
	private JLabel lblUsername = new JLabel("Korisnicko ime: ");
	private JTextField txtUsername = new JTextField(25);
	private JLabel lblLozinka = new JLabel("Lozinka: ");
	private JPasswordField pwLozinka = new JPasswordField(25);
	private JButton btnOk = new JButton("Prijavi se");
	private JButton btnCancel = new JButton("Odustani");
	

	JLabel lblServisLogo = new JLabel(new ImageIcon("src/slike/logo.png"));
	
	
	
	public Login() {
		
		setTitle("Prijava na servis automobila");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
		initActions();
		pack();
		setLocationRelativeTo(null);
	
	}
			
	public void initGUI() {
		MigLayout mig = new MigLayout("wrap 2", "[][]", "[]15[]15[]10[]");
		setLayout(mig);
		add(lblServisLogo, "span 2");
		
		add(lblPoruka, "span 2");
		add(lblUsername);
		add(txtUsername);
		add(lblLozinka);
		add(pwLozinka);
		add(new JLabel()); //prazno mesto 
		add(btnOk, "split 2");
		add(btnCancel);
		
		getRootPane().setDefaultButton(btnOk);
	}
	public void initActions() {
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login.this.dispose();
				Login.this.setVisible(false);
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String korisnickoIme = txtUsername.getText().trim();
				String lozinka = new String(pwLozinka.getPassword()).trim();
				
				if(korisnickoIme.equals("") || lozinka.equals("")) {
					JOptionPane.showMessageDialog(null, "Niste uneli sve podatke za prijavu.", "Greska", JOptionPane.WARNING_MESSAGE);
				}
				
				else {
					Korisnik prijavljeni = UcitavanjePodataka.login(korisnickoIme, lozinka);
					if(prijavljeni == null) {
						JOptionPane.showMessageDialog(null, "Pogresni login podaci.", "Greska", JOptionPane.WARNING_MESSAGE);
					}else {
						Login.this.dispose();
						Login.this.setVisible(false);
						if(prijavljeni instanceof Musterija) {
							MusterijaProzor musterijaProzor = new MusterijaProzor((Musterija)prijavljeni);
							musterijaProzor.setVisible(true);
						}
						else if(prijavljeni instanceof Serviser) {
							ServiserProzor serviserProzor = new ServiserProzor((Serviser)prijavljeni);
							serviserProzor.setVisible(true);
						}
						else if(prijavljeni instanceof Administrator) {
							AdministratorProzor administratorProzor = new AdministratorProzor((Administrator)prijavljeni);
							administratorProzor.setVisible(true);
						}
						
					}
				}
			}
		});
}
}

