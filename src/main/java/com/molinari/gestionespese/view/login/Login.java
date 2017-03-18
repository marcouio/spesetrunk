package com.molinari.gestionespese.view.login;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.LabelTitolo;
import com.molinari.gestionespese.view.font.TextFieldF;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;

public class Login {

	private final TextFieldF user;
	private final TextFieldF pass;
	private JDialog dialog = new JDialog();
	
	public Login() {
		getDialog().getContentPane().setLayout(null);
		getDialog().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
		getDialog().setBounds(400, 300, 400, 220);
		getDialog().setTitle("Login");
		final LabelListaGruppi lblUsername = new LabelListaGruppi("Username");
		lblUsername.setBounds(83, 69, 88, 25);
		getDialog().getContentPane().add(lblUsername);

		final LabelListaGruppi lblPassword = new LabelListaGruppi("Password");
		lblPassword.setBounds(221, 68, 88, 25);
		getDialog().getContentPane().add(lblPassword);

		user = new TextFieldF();
		user.setBounds(83, 94, 86, 25);
		getDialog().getContentPane().add(user);
		user.setColumns(10);

		pass = new TextFieldF();
		pass.setColumns(10);
		pass.setBounds(221, 94, 86, 25);
		getDialog().getContentPane().add(pass);

		final LabelTitolo lblLogin = new LabelTitolo("LOGIN");
		lblLogin.setBounds(171, 25, 57, 32);
		getDialog().getContentPane().add(lblLogin);

		final ButtonF btnEntra = new ButtonF("Entra");
		btnEntra.setBounds(148, 148, 91, 23);
		getDialog().getContentPane().add(btnEntra);

		btnEntra.addActionListener(getListenerLogin());

	}

	private AscoltatoreAggiornatoreNiente getListenerLogin() {
		return new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final WrapUtenti utentiwrap = new WrapUtenti();
				final IUtenti utente = utentiwrap.selectByUserAndPass(user.getText(), pass.getText());
				if (utente != null) {
					login(utente);
				} else {
					Alert.segnalazioneErroreGrave("Login non effettuato: username o password non corretti");
				}

			}
		};
	}

	private void login(final IUtenti utente) {
		final Impostazioni impostazioni = Impostazioni.getSingleton();
		try {
			Controllore.getSingleton().setUtenteLogin(utente);
			impostazioni.getUtente().setText(utente.getusername());
			//  creare comando per sostituire tutto con nuova  gestione
			AggiornatoreManager.aggiornamentoPerImpostazioni();
			Alert.info("Benvenuto, " + utente.getnome(), Alert.TITLE_OK);
		} catch (final Exception e1) {
			ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
		}
		impostazioni.repaint();
		getDialog().dispose();
	}

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}
}
