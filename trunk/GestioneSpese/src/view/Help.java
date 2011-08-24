package view;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import business.ascoltatori.AscoltatoreAggiornatoreNiente;

import view.font.ButtonF;
import view.font.LabelListaGruppi;

public class Help extends JDialog {

	private static final long serialVersionUID = 1L;

	public Help() {
		setLayout(null);
		ImageIcon image = new ImageIcon("imgUtil/index1.jpeg");
		JLabel label = new JLabel("");
		label.setIcon(image);
		label.setBounds(36, 55, 138, 297);
		add(label);

		JLabel lblGestionespese = new LabelListaGruppi("GestioneSpese per la gestione economica familiare");
		lblGestionespese.setBounds(184, 45, 391, 28);
		add(lblGestionespese);

		JLabel lblVersione = new LabelListaGruppi("Versione: 1.0.0");
		lblVersione.setBounds(184, 85, 240, 28);
		add(lblVersione);

		JLabel lblVersione2 = new LabelListaGruppi("Versione: 1.0.0");
		lblVersione2.setBounds(184, 85, 240, 28);
		add(lblVersione2);

		JLabel lblMarcoMolinari = new LabelListaGruppi("Copyright Marco Molinari 2010. All right reserved.");
		lblMarcoMolinari.setBounds(184, 124, 327, 28);
		add(lblMarcoMolinari);

		JLabel help = new LabelListaGruppi("Clicca qui per accedere all'Help");
		help.setText("Clicca sul bottone per l'Help");
		help.setBounds(184, 163, 215, 28);

		add(help);

		ButtonF btnHelp = new ButtonF();
		btnHelp.setText("Help");
		btnHelp.setBounds(389, 166, 91, 23);
		btnHelp.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			protected void actionPerformedOverride(ActionEvent e) {
				super.actionPerformedOverride(e);
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(new File("help.pdf"));
				} catch (Exception e1) {
					Alert.operazioniSegnalazioneErroreGrave(e1.getMessage());
				}

			}
		});
		add(btnHelp);
	}
}
