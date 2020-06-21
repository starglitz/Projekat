package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import entiteti.Korisnik;
import entiteti.Musterija;
import entiteti.Serviser;
import gui.Musterija.PrikazServisa;
import gui.Musterija.Zakazivanje;
import net.miginfocom.swing.MigLayout;


public class MusterijaProzor extends JFrame {
	
	private JLabel poruka = new JLabel("Uspesno ste se prijavili! ");
	
	
	private JButton zakaziServis = new JButton("Zakazi servis automobila");
	private JButton prikaziServise = new JButton("Prikazi sve zakazane servise");
	private JButton btnIzadji = new JButton("Izadji iz aplikacije");
	private JButton btnOdjaviSe = new JButton("Odjavi se");
	
	JLabel lblOsobaLogo = new JLabel(new ImageIcon("src/slike/osoba.png"));

	
	public MusterijaProzor(Musterija musterija) {
		setTitle("Meni za musteriju: " + musterija.getIme() + " " + musterija.getPrezime());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(musterija);
		initActions(musterija);
		pack();
		setLocationRelativeTo(null);
		
		
	}
	
	public void initGUI(Musterija musterija) {
			
		MigLayout mig = new MigLayout("wrap 2", "[center][left][c]", "[center]10[center]10[center]10[center]20[center][b]");
		
		setLayout(mig);
		add(poruka);
		add(lblOsobaLogo, "span 1 5");
		add(new JLabel("Broj nagradnih bodova: " + musterija.getBrBodova()));
		add(zakaziServis);
		add(prikaziServise);
		add(btnOdjaviSe, "split 2");
		add(btnIzadji);
		
	}
	
	public void initActions(Musterija musterija) {
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MusterijaProzor.this.dispose();
				MusterijaProzor.this.setVisible(false);
			}
		});
		
		
		
		btnOdjaviSe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MusterijaProzor.this.dispose();
				MusterijaProzor.this.setVisible(false);
				
				Login login = new Login();
				login.setVisible(true);
			}
		});
		
		zakaziServis.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Zakazivanje zakazivanje = new Zakazivanje(musterija);
				zakazivanje.setVisible(true);
				
			}
		});
		
		prikaziServise.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazServisa prikazServisa = new PrikazServisa(musterija);
				prikazServisa.setVisible(true);
				
			}
		});
}
}
