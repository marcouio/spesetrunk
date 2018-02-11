package com.molinari.gestionespese.view.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.molinari.gestionespese.business.cache.CacheUtenti;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

public class RegisterListener implements ActionListener {
	
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
		final int idUtente = CacheUtenti.getSingleton().getMaxId() + 1;
		utente.setidUtente(idUtente);
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
				if(utentiwrap.insert(utente)) {
					CacheUtenti.getSingleton().getCache().put(utente.getIdEntita(), utente);
				}
			} else {
				Alert.segnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("userpresente"));
			}
			endOperation();
		} else {
			Alert.segnalazioneErroreGrave(I18NManager.getSingleton().getMessaggio("userfieldnotvalued"));
		}

	}

	protected void endOperation() {
		//Auto-generated method stub
	}
}