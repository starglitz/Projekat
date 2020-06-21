package gui.Administrator;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import entiteti.Administrator;
import net.miginfocom.swing.MigLayout;


public class PrikazKorisnika extends JFrame {
	private JButton btnMusterije = new JButton("Upravljaj musterijama");
	private JButton btnServiseri = new JButton("Upravljaj serviserima");
	private JButton btnAdministratori = new JButton("Upravljaj administratorima");
	
	
	public PrikazKorisnika(Administrator administrator) {
		setTitle("Prikaz korisnika");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI(administrator);
		initActions(administrator);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void initGUI(Administrator administrator) {
		MigLayout mig = new MigLayout("wrap 2", "[center][center]", "[center][center]");
		setLayout(mig);
		add(btnMusterije);
		add(btnServiseri);
		add(btnAdministratori, "span 2");
		
	
	}
	
	public void initActions(Administrator administrator) {
		btnMusterije.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazMusterija musterijeP = new PrikazMusterija(administrator);
				musterijeP.setVisible(true);
				PrikazKorisnika.this.dispose();
				PrikazKorisnika.this.setVisible(false);
				
			}
		});
		
		btnServiseri.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazServisera serviseriP = new PrikazServisera(administrator);
				serviseriP.setVisible(true);
				PrikazKorisnika.this.dispose();
				PrikazKorisnika.this.setVisible(false);
				
			}
		});
		
		btnAdministratori.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrikazAdministratora adminiP = new PrikazAdministratora(administrator);
				adminiP.setVisible(true);
				PrikazKorisnika.this.dispose();
				PrikazKorisnika.this.setVisible(false);
				
			}
		});
	}
}
