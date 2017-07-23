package com.molinari.gestionespese.view.login;

import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.JTextField;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;
import com.molinari.gestionespese.view.impostazioni.Impostazioni;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

public class ListenerLogin extends AscoltatoreAggiornatoreNiente {

	private JTextField user;
	private JTextField pass;
	/**
	 * @param login
	 */
	public ListenerLogin(JTextField user, JTextField pass) {
		this.pass = pass;
		this.user = user;
	}

	@Override
	public void actionPerformedOverride(final ActionEvent e) {
		final WrapUtenti utentiwrap = new WrapUtenti();
		
		if(user.getText() == null){
			String messaggio = I18NManager.getSingleton().getMessaggio("userempty");
			Alert.segnalazioneErroreGrave(messaggio);
		}else if(pass.getText() == null){
			String messaggio = I18NManager.getSingleton().getMessaggio("passempty");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		
		final IUtenti utente = utentiwrap.selectByUserAndPass(user.getText(), pass.getText());
		if (utente != null) {
			this.login(utente);
		} else {
			String messaggio = I18NManager.getSingleton().getMessaggio("LoginFailed");
			Alert.segnalazioneErroreGrave(messaggio);
		}

	}
	
 private void login(final IUtenti utente) {
		
		ControlloreBase.getSingleton().setUtenteLogin(utente);
		final Impostazioni impostazioni = Impostazioni.getSingleton();
		try {
			impostazioni.getUtente().setText(utente.getusername());
			//  creare comando per sostituire tutto con nuova  gestione
			AggiornatoreManager.aggiornamentoPerImpostazioni();
			String messaggio = I18NManager.getSingleton().getMessaggio("welcome");
			Alert.info(messaggio + ", " + utente.getnome(), Alert.TITLE_OK);
			endOperation();
		} catch (final Exception e1) {
			ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
		}
		impostazioni.repaint();
	}

 	protected void endOperation() {
 		//Auto-generated method stub
 	}
}