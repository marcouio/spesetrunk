package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.wrapper.WrapUtenti;

public class CacheUtenti extends AbstractCacheBase<IUtenti> {

	private static CacheUtenti singleton;
	private final WrapUtenti utentiDAO = new WrapUtenti();

	private CacheUtenti() {
		setCache(new HashMap<String, IUtenti>());
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
			final IUtenti utente = (IUtenti) element;
			if (utente.getusername().equals(username)) {
				ok = true;
			}
		}
		return ok;
	}

	public IUtenti getUtente(String id) {
		return getObjectById(utentiDAO, id);
	}

	public Map<String, IUtenti> getAllUtenti() {
		return getAll(utentiDAO);
	}

	public List<IUtenti> getVettoreUtenti() {
		final List<IUtenti> utenti = new ArrayList<>();
		final Map<String, IUtenti> mappa = this.getAllUtenti();
		final IUtenti[] lista = mappa.values().toArray(new IUtenti[0]);
		for (final IUtenti element : lista) {
			utenti.add(element);
		}
		return utenti;
	}

	public Object[] getArrayUtenti() {
		final Map<String, IUtenti> mappa = this.getAllUtenti();
		return mappa.values().toArray();
	}
}
