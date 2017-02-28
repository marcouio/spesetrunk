package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;

public class CacheUtenti extends AbstractCacheBase<Utenti> {

	private static CacheUtenti singleton;
	private final WrapUtenti utentiDAO = new WrapUtenti();

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
		final Object[] utenti = getArrayUtenti();
		for (final Object element : utenti) {
			final Utenti utente = (Utenti) element;
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
		final List<Utenti> utenti = new ArrayList<>();
		final Map<String, Utenti> mappa = this.getAllUtenti();
		final Utenti[] lista = (Utenti[]) mappa.values().toArray();
		for (final Utenti element : lista) {
			utenti.add(element);
		}
		return utenti;
	}

	public Object[] getArrayUtenti() {
		final Map<String, Utenti> mappa = this.getAllUtenti();
		return mappa.values().toArray();
	}
}
