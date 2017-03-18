package com.molinari.gestionespese.view;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.molinari.gestionespese.business.AltreUtil;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.utility.messages.I18NManager;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;

public class Help extends JDialog {

	private static final long serialVersionUID = 1L;

	public Help() {
		setLayout(null);
		final ImageIcon image = new ImageIcon(AltreUtil.IMGUTILPATH+"index1.jpeg");
		final JLabel label = new JLabel("");
		label.setIcon(image);
		label.setBounds(36, 55, 138, 297);
		add(label);

		final JLabel lblGestionespese = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("description"));
		lblGestionespese.setBounds(184, 45, 391, 28);
		add(lblGestionespese);

		final JLabel lblVersione = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("version"));
		lblVersione.setBounds(184, 85, 240, 28);
		add(lblVersione);

		final JLabel lblVersione2 = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("version"));
		lblVersione2.setBounds(184, 85, 240, 28);
		add(lblVersione2);

		final JLabel lblMarcoMolinari = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("copyright"));
		lblMarcoMolinari.setBounds(184, 124, 327, 28);
		add(lblMarcoMolinari);

		final JLabel help = new LabelListaGruppi(I18NManager.getSingleton().getMessaggio("clickherehelp"));
		help.setText(I18NManager.getSingleton().getMessaggio("cliccahelp"));
		help.setBounds(184, 163, 215, 28);

		add(help);

		final ButtonF btnHelp = new ButtonF();
		btnHelp.setText(I18NManager.getSingleton().getMessaggio("help"));
		btnHelp.setBounds(389, 166, 91, 23);
		btnHelp.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			protected void actionPerformedOverride(ActionEvent e) {
				super.actionPerformedOverride(e);
				final Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(new File("help.pdf"));
				} catch (final Exception e1) {
					Alert.segnalazioneErroreWarning(e1.getMessage());
					ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
				}

			}
		});
		add(btnHelp);
	}
}
