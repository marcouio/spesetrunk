package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import command.javabeancommand.AbstractOggettoEntita;

import view.impostazioni.Impostazioni;
import business.Controllore;
import domain.Entrate;
import domain.Utenti;
import domain.wrapper.WrapEntrate;

public class CacheEntrate extends AbstractCacheBase {

	private static CacheEntrate singleton;

	private CacheEntrate() {
		cache = new HashMap<String, AbstractOggettoEntita>();
		caricata = false;
	}

	public static CacheEntrate getSingleton() {
		if (singleton == null) {
			synchronized (CacheEntrate.class) {
				if (singleton == null) {
					singleton = new CacheEntrate();
				}
			} // if
		} // if
		return singleton;
	}

	WrapEntrate entrateDAO = new WrapEntrate();

	public Entrate getEntrate(final String id) {
		Entrate entrate = (Entrate) cache.get(id);
		if (entrate == null) {
			entrate = caricaEntrate(id);
			if (entrate != null) {
				cache.put(id, entrate);
			}
		}
		return (Entrate) cache.get(id);
	}

	private Entrate caricaEntrate(final String id) {
		return (Entrate) new WrapEntrate().selectById(Integer.parseInt(id));
	}

	public Map<String, AbstractOggettoEntita> chargeAllEntrate() {
		final Vector<Object> entrate = entrateDAO.selectAll();
		if (entrate != null) {
			for (int i = 0; i < entrate.size(); i++) {
				final Entrate entrata = (Entrate) entrate.get(i);
				final int id = entrata.getidEntrate();
				if (cache.get(id) == null) {
					cache.put(Integer.toString(id), entrata);
				}
			}
		} else {
			cache = new HashMap<String, AbstractOggettoEntita>();
		}
		caricata = true;
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllEntrate() {
		if (caricata) {
			return cache;
		} else {
			return chargeAllEntrate();
		}
	}

	public ArrayList<Entrate> getAllEntrateForUtente() {
		final ArrayList<Entrate> listaEntrate = new ArrayList<Entrate>();
		final Map<String, AbstractOggettoEntita> mappa = getAllEntrate();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				final Entrate entrata = (Entrate) mappa.get(chiavi.next());
				if (entrata != null && (entrata.getUtenti() != null || entrata.getUtenti().getidUtente() != 0)) {
					if (entrata.getUtenti().getidUtente() == utente.getidUtente()) {
						listaEntrate.add(entrata);
					}
				}
			}
		}
		return listaEntrate;
	}

	public ArrayList<Entrate> getAllEntrateForUtenteEAnno() {
		final ArrayList<Entrate> listaEntrate = new ArrayList<Entrate>();
		final Map<String, AbstractOggettoEntita> mappa = getAllEntrate();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		final String annoDaText = Impostazioni.getSingleton().getAnnotextField().getText();

		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				final Entrate entrata = (Entrate) mappa.get(chiavi.next());
				if (entrata != null && (entrata.getUtenti() != null && entrata.getUtenti().getidUtente() != 0)) {
					final String annoEntrata = entrata.getdata().substring(0, 4);
					if (entrata.getUtenti().getidUtente() == utente.getidUtente() && annoEntrata.equals(annoDaText)) {
						listaEntrate.add(entrata);
					}
				}
			}
		}
		return listaEntrate;
	}

	public int getMaxId() {
		int maxId = 0;
		final Map<String, AbstractOggettoEntita> mappa = getAllEntrate();
		final Iterator<String> chiavi = mappa.keySet().iterator();
		if (mappa != null) {
			while (chiavi.hasNext()) {
				final Entrate entrata = (Entrate) mappa.get(chiavi.next());
				if (entrata != null) {
					final int idEntrate = entrata.getidEntrate();
					if (idEntrate > maxId) {
						maxId = idEntrate;
					}
				}
			}
		}
		return maxId;
	}

}
