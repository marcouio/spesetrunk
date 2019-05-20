package com.molinari.gestionespese.view.login;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Font;
import java.util.logging.Level;

import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.graphic.component.button.ButtonBase;
import com.molinari.utility.graphic.component.label.Label;
import com.molinari.utility.graphic.component.label.LabelTestoPiccolo;
import com.molinari.utility.graphic.component.passwordfield.PasswordFieldBase;
import com.molinari.utility.graphic.component.textfield.TextFieldBase;
import com.molinari.utility.messages.I18NManager;

public class Login {

	final TextFieldBase user;
	final JPasswordField pass;
	private JDialog dialog = new JDialog();
	
	public Login() {
		getDialog().getContentPane().setLayout(null);
		getDialog().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getDialog().setModalityType(ModalityType.APPLICATION_MODAL);
		getDialog().setBounds(400, 300, 400, 220);
		getDialog().setTitle("Login");
		final LabelTestoPiccolo lblUsername = new LabelTestoPiccolo("Username", getDialog().getContentPane());
		lblUsername.setBounds(83, 69, 88, 25);
		getDialog().getContentPane().add(lblUsername);

		final LabelTestoPiccolo lblPassword = new LabelTestoPiccolo("Password", getDialog().getContentPane());
		lblPassword.setBounds(221, 68, 88, 25);
		getDialog().getContentPane().add(lblPassword);

		user = new TextFieldBase(getDialog().getContentPane());
		user.setBounds(83, 94, 86, 25);
		getDialog().getContentPane().add(user);
		user.setColumns(10);

		pass = new PasswordFieldBase(getDialog().getContentPane());
		pass.setColumns(10);
		pass.setBounds(221, 94, 86, 25);
		getDialog().getContentPane().add(pass);

		final Label lblLogin = new Label("LOGIN", getDialog().getContentPane());
		lblLogin.setBounds(171, 25, 57, 32);
		getDialog().getContentPane().add(lblLogin);

		final ButtonBase btnEntra = new ButtonBase(I18NManager.getSingleton().getMessaggio("enter"), getDialog().getContentPane());
		btnEntra.setBounds(148, 148, 91, 23);
		getDialog().getContentPane().add(btnEntra);

		btnEntra.addActionListener(getListenerLogin());

	}

	private AscoltatoreAggiornatoreNiente getListenerLogin() {
		return new ListenerLogin(this.user, this.pass){
			@Override
			protected void endOperation() {
				getDialog().dispose();
			}
		};
	}

	
	void login(final IUtenti utente) {
		
		ControlloreBase.getSingleton().setUtenteLogin(utente);
		final Impostazioni impostazioni = Impostazioni.getSingleton();
		try {
			impostazioni.getUtente().setText(utente.getusername());
			//  creare comando per sostituire tutto con nuova  gestione
			AggiornatoreManager.aggiornamentoPerImpostazioni();
			String messaggio = I18NManager.getSingleton().getMessaggio("welcome");
			Alert.info(messaggio + ", " + utente.getnome(), Alert.TITLE_OK);
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
