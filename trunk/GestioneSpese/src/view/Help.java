package view;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

import view.font.LabelTesto;
import javax.swing.JButton;

public class Help extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Help() {
		setLayout(null);
		ImageIcon image = new ImageIcon("imgUtil/index1.jpeg");
		JLabel label = new JLabel("");
		label.setIcon(image);
		label.setBounds(36, 55, 138, 297);
		add(label);
		
		JLabel lblGestionespese = new LabelTesto("GestioneSpese per la gestione economica familiare");
		lblGestionespese.setBounds(184, 55, 320, 14);
		add(lblGestionespese);
		
		JLabel lblVersione = new LabelTesto("Versione: 1.0.0");
		lblVersione.setBounds(184, 85, 240, 28);
		add(lblVersione);
		
		JLabel lblMarcoMolinari = new LabelTesto("Copyright Marco Molinari 2010. All right reserved.");
		lblMarcoMolinari.setBounds(184, 124, 327, 28);
		add(lblMarcoMolinari);
		
		JLabel help = new LabelTesto("Clicca qui per accedere all'Help");
		help.setText("Clicca sul bottone per l'Help");
		help.setBounds(184, 163, 153, 28);
		
		add(help);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(333, 163, 91, 23);
		btnHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(new File("help.pdf"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		add(btnHelp);
		
	}
}
