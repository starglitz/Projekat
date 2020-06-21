package gui.Musterija;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import IO.ServisAutomobilaIO;
import IO.UcitavanjePodataka;
import entiteti.Automobil;
import entiteti.Musterija;
import entiteti.Povezivanje;
import entiteti.ServisAutomobila;
import enumeracije.StatusServisa;
import gui.Serviser.KreiranjeServisa;
import net.miginfocom.swing.MigLayout;

public class Zakazivanje extends JFrame {
	private JLabel poruka= new JLabel("Unesite podatke o servisu: ");
	private JComboBox<String> cbAutomobili = new JComboBox<String>();
	private JLabel lblAutomobil = new JLabel("Automobil: ");
	private JLabel lblOpis = new JLabel("Opis: ");
	private JTextField txtOpis = new JTextField(20);
	private JButton btnOk = new JButton("Zakazi servis");
	private JButton btnCancel = new JButton("Otkazi");
	
	
	public Zakazivanje(Musterija musterija) {
		setTitle("Zakazivanje servisa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(musterija);
		initActions(musterija);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Musterija musterija) {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[][]20[]");
		setLayout(layout);
		
		ArrayList<Automobil> automobiliMusterije = Povezivanje.getAutomobiliMusterije(musterija.getId());
		for(Automobil auto : automobiliMusterije) {
			cbAutomobili.addItem(auto.getId());
		}
		
		add(poruka, "span 2");
		add(lblAutomobil);
		add(cbAutomobili);
		add(lblOpis);
		add(txtOpis);
		add(new JLabel());
		add(btnOk, "split 2");
		add(btnCancel);
	}
	
	public void initActions(Musterija musterija) {
		

		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Zakazivanje.this.dispose();
				Zakazivanje.this.setVisible(false);
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int random = (int )(Math.random() * 999999 + 100000);
				String servisId = Integer.toString(random);
				String automobilId = (String)cbAutomobili.getSelectedItem();
				Automobil automobil = Povezivanje.getAutomobilPoId(automobilId);
				String opis = txtOpis.getText().trim();
				
				ServisAutomobila servis = new ServisAutomobila(servisId, automobil, opis, StatusServisa.ZAKAZAN, false);
				UcitavanjePodataka.addServis(servis);
				ServisAutomobilaIO.ServisAutomobilaWriter(UcitavanjePodataka.getServisi());
				JOptionPane.showMessageDialog(null, "Uspesno ste zakazali servis!", "Poruka", JOptionPane.PLAIN_MESSAGE);
				Zakazivanje.this.dispose();
				Zakazivanje.this.setVisible(false);
			}
		});
	}
	
	
}
