package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;

import java.util.List;

import command.javabeancommand.AbstractOggettoEntita;

public class CacheUtenti extends AbstractCacheBase<Utenti> {

	private static CacheUtenti singleton;
	private WrapUtenti utentiDAO = new WrapUtenti();

	private CacheUtenti() {
		setCache(new HashMap<String, Utenti>());
	}

	public static CacheUtenti getSingleton() {

		if (singleton == null) {
			singleton = new CacheUtenti();
		}
		return singleton;
	}


	public boolean checkUtentePerUsername(String username) {
		boolean ok = false;
		Object[] utenti = getArrayUtenti();
		for (int i = 0; i < utenti.length; i++) {
			Utenti utente = (Utenti) utenti[i];
			if (utente.getusername().equals(username)) {
				ok = true;
			}
		}
		return ok;
	}

	public Utenti getUtente(String id) {
		return getObjectById(utentiDAO, id);
	}

	public Map<String, Utenti> getAllUtenti() {
		return getAll(utentiDAO);
	}

	public List<Utenti> getVettoreUtenti() {
		List<Utenti> utenti = new ArrayList<>();
		Map<String, Utenti> mappa = this.getAllUtenti();
		Utenti[] lista = (Utenti[]) mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			utenti.add(lista[i]);
		}
		return utenti;
	}

	public Object[] getArrayUtenti() {
		Map<String, Utenti> mappa = this.getAllUtenti();
		return mappa.values().toArray();
	}
}
