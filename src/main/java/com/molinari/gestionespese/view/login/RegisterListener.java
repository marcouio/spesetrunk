package com.molinari.gestionespese.view.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;
import com.molinari.utility.graphic.component.alert.Alert;

public class RegisterListener implements ActionListener {
	/**
	 * 
	 */
	private final JTextField nome;
	private final JTextField cognome;
	private final JTextField user;
	private final JTextField pass;

	public RegisterListener(JTextField nome, JTextField cognome, JTextField user, JTextField pass) {
		this.nome = nome;
		this.cognome = cognome;
		this.user = user;
		this.pass = pass;
	}
	
	private Utenti fillUtente(final String sNome, final String sCognome, final String sPass, final String sUser) {
		final Utenti utente = new Utenti();
		utente.setNome(sNome);
		utente.setCognome(sCognome);
		utente.setpassword(sPass);
		utente.setusername(sUser);
		return utente;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		final String sNome = nome.getText();
		final String sCognome = cognome.getText();
		final String sPass = pass.getText();
		final String sUser = user.getText();
		final WrapUtenti utentiwrap = new WrapUtenti();
		if (!"".equals(sNome) && !"".equals(sCognome) && !"".equals(sPass) && !"".equals(sUser)) {
			final Utenti utente = fillUtente(sNome, sCognome, sPass, sUser);
			final boolean ok = CacheUtenti.getSingleton().checkUtentePerUsername(sUser);
			if (!ok) {
				utentiwrap.insert(utente);
			} else {
				Alert.segnalazioneErroreGrave("Username già presente, sceglierne un altro");
			}
			endOperation();
		} else {
			Alert.segnalazioneErroreGrave("Utente non creato: Tutti i campi devono essere valorizzati");
		}

	}

	protected void endOperation() {
		//Auto-generated method stub
	}
}