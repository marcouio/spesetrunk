package com.molinari.gestionespese.view.login;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.LabelTitolo;
import com.molinari.gestionespese.view.font.TextFieldF;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

public class Login extends JDialog {

	private static final long serialVersionUID = 1L;
	private final TextFieldF user;
	private final TextFieldF pass;

	public Login() {
		getContentPane().setLayout(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		this.setBounds(400, 300, 400, 220);
		this.setTitle("Login");
		final LabelListaGruppi lblUsername = new LabelListaGruppi("Username");
		lblUsername.setBounds(83, 69, 88, 25);
		getContentPane().add(lblUsername);

		final LabelListaGruppi lblPassword = new LabelListaGruppi("Password");
		lblPassword.setBounds(221, 68, 88, 25);
		getContentPane().add(lblPassword);

		user = new TextFieldF();
		user.setBounds(83, 94, 86, 25);
		getContentPane().add(user);
		user.setColumns(10);

		pass = new TextFieldF();
		pass.setColumns(10);
		pass.setBounds(221, 94, 86, 25);
		getContentPane().add(pass);

		final LabelTitolo lblLogin = new LabelTitolo("LOGIN");
		lblLogin.setBounds(171, 25, 57, 32);
		getContentPane().add(lblLogin);

		final ButtonF btnEntra = new ButtonF("Entra");
		btnEntra.setBounds(148, 148, 91, 23);
		getContentPane().add(btnEntra);

		btnEntra.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final WrapUtenti utentiwrap = new WrapUtenti();
				final Utenti utente = utentiwrap.selectByUserAndPass(user.getText(), pass.getText());
				if (utente != null) {
					final Impostazioni impostazioni = Impostazioni.getSingleton();
					try {
						Controllore.getSingleton().setUtenteLogin(utente);
						impostazioni.getUtente().setText(utente.getusername());
						// TODO creare comando per sostituire tutto con nuova
						// gestione
						AggiornatoreManager.aggiornamentoPerImpostazioni();
						Alert.info("Benvenuto, " + utente.getnome(), Alert.TITLE_OK);
					} catch (final Exception e1) {
						e1.printStackTrace();
					}
					impostazioni.repaint();
					dispose();
				} else {
					Alert.segnalazioneErroreGrave("Login non effettuato: username o password non corretti");
				}

			}
		});

	}
}
