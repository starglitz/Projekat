package gui.Serviser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import IO.ServisAutomobilaIO;
import IO.UcitavanjePodataka;
import entiteti.ServisAutomobila;
import entiteti.Serviser;
import net.miginfocom.swing.MigLayout;

public class IzmenaServisa extends JFrame {
	private JLabel poruka = new JLabel("Unesite izmene: ");
	private JLabel lblOpis = new JLabel("Opis servisa: ");
	private JTextField txtOpis = new JTextField(20);
	private JLabel lblTroskovi = new JLabel("Troskovi servisa: ");
	private JTextField txtTroskovi = new JTextField(20);
	
	private JButton btnOK = new JButton("Potvrdi");
	private JButton btnCancel =  new JButton("Otkazi");
	
	public IzmenaServisa(Serviser serviser, ServisAutomobila servis) {
		setTitle("Izmena servisa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(serviser, servis);
		initActions(serviser, servis);
		pack();
		setLocationRelativeTo(null);
	}
	
	
	public void initGUI(Serviser serviser, ServisAutomobila servis) {
		MigLayout layout = new MigLayout("wrap 2", "[][]", "[]15[][]20[]");
		setLayout(layout);
		
		add(poruka, "span 2");
		add(lblOpis);
		add(txtOpis);
		txtOpis.setText(servis.getOpis());
		add(lblTroskovi);
		add(txtTroskovi);
		txtTroskovi.setText(Integer.toString(servis.getTroskovi()));
		add(new JLabel());
		add(btnOK, "split 2");
		add(btnCancel);
		
	}
	
	public void initActions(Serviser serviser, ServisAutomobila servis) {
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IzmenaServisa.this.dispose();
				IzmenaServisa.this.setVisible(false);
			}
		});
		
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ok = validacija(serviser, servis);
				if(ok == true) {
					String opis = txtOpis.getText().trim();
					String troskoviStr = txtTroskovi.getText().trim();
					
					int troskovi = Integer.parseInt(troskoviStr);
					servis.setOpis(opis);
					servis.setTroskovi(troskovi);
				
				}
				ServisAutomobilaIO.ServisAutomobilaWriter(UcitavanjePodataka.getServisi());
				IzmenaServisa.this.dispose();
				IzmenaServisa.this.setVisible(false);
			}
		});
	}
	
	private boolean validacija(Serviser serviser, ServisAutomobila servis) {
		String poruka = "Molimo popravite sledece greske u unosu:\n";
		boolean ok = true;
		if(txtOpis.getText().trim().equals("")) {
			poruka += "- Unesite opis\n";
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
	
	
}
