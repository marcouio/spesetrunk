package com.molinari.gestionespese.view.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.util.logging.Level;

import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.view.font.ButtonF;
import com.molinari.gestionespese.view.font.LabelListaGruppi;
import com.molinari.gestionespese.view.font.LabelTitolo;
import com.molinari.gestionespese.view.font.TextFieldF;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

public class Login {

	final TextFieldF user;
	final JPasswordField pass;
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

		pass = new JPasswordField();
		pass.setFont(new Font("Eras Light ITC", Font.PLAIN, 12));
		pass.setBackground(Color.GRAY);
		pass.setForeground(Color.white);
		pass.setColumns(10);
		pass.setBounds(221, 94, 86, 25);
		getDialog().getContentPane().add(pass);

		final LabelTitolo lblLogin = new LabelTitolo("LOGIN");
		lblLogin.setBounds(171, 25, 57, 32);
		getDialog().getContentPane().add(lblLogin);

		final ButtonF btnEntra = new ButtonF(I18NManager.getSingleton().getMessaggio("enter"));
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
