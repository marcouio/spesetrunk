package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import command.javabeancommand.AbstractOggettoEntita;

import domain.Utenti;
import domain.wrapper.WrapUtenti;

public class CacheUtenti extends AbstractCacheBase<Utenti> {

	private static CacheUtenti singleton;
	private WrapUtenti utentiDAO = new WrapUtenti();

	private CacheUtenti() {
		cache = new HashMap<String, Utenti>();
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
		Utenti utenti = (Utenti) cache.get(id);
		if (utenti == null) {
			utenti = caricaUtenti(id);
			if (utenti != null) {
				cache.put(id, utenti);
			}
		}
		return (Utenti) cache.get(id);
	}

	private Utenti caricaUtenti(String id) {
		return (Utenti) new WrapUtenti().selectById(Integer.parseInt(id));
	}

	public Map<String, Utenti> chargeAllUtenti() {
		List<Object> utenti = utentiDAO.selectAll();
		if (utenti != null) {
			for (int i = 0; i < utenti.size(); i++) {
				Utenti utente = (Utenti) utenti.get(i);
				int id = utente.getidUtente();
				if (cache.get(id) == null) {
					cache.put(Integer.toString(id), utente);
				}
			}
			caricata = true;
		}
		return cache;
	}

	public Map<String, Utenti> getAllUtenti() {
		if (caricata)
			return cache;
		else
			return chargeAllUtenti();
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
