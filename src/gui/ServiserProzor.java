package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import entiteti.Serviser;
import gui.Serviser.prikazServisaProzor;
import net.miginfocom.swing.MigLayout;

public class ServiserProzor extends JFrame {
	private JLabel lblPoruka = new JLabel("Uspesno ste se prijavili! ");
	private JButton btnUpravljaj = new JButton("Upravljaj servisima");

	private JButton btnOdjaviSe = new JButton("Odjavi se");
	private JButton btnIzadji = new JButton("Izadji iz aplikacije");
	JLabel lblOsobaLogo = new JLabel(new ImageIcon("src/slike/osoba.png"));
	

	public ServiserProzor(Serviser serviser) {
		setTitle("Meni za servisera: " + serviser.getIme() + " " + serviser.getPrezime());		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(serviser);
		initActions(serviser);
		pack();
		setLocationRelativeTo(null);
		
	}
	
	public void initGUI(Serviser serviser) {
		MigLayout mig = new MigLayout("wrap 2", "[center][left]", "[center]30[center][center]");	
		setLayout(mig);
		
		add(lblPoruka);
		add(lblOsobaLogo, "span 1 3");
		add(btnUpravljaj);
		add(btnOdjaviSe, "split 2");
		add(btnIzadji);
	}
	
	public void initActions(Serviser serviser) {
		btnIzadji.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServiserProzor.this.dispose();
				ServiserProzor.this.setVisible(false);
			}
		});
	
		btnOdjaviSe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServiserProzor.this.dispose();
				ServiserProzor.this.setVisible(false);
				
				Login login = new Login();
				login.setVisible(true);
			}
		});
		
	btnUpravljaj.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			prikazServisaProzor prikaz = new prikazServisaProzor(serviser);
			prikaz.setVisible(true);
		}
	});
		
	}
}
