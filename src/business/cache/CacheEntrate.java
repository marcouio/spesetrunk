package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import business.Controllore;
import domain.Entrate;
import domain.Utenti;
import domain.wrapper.WrapEntrate;
import view.impostazioni.Impostazioni;

public class CacheEntrate extends AbstractCacheBase<Entrate> {

	private static CacheEntrate singleton;
	private WrapEntrate entrateDAO = new WrapEntrate();

	private CacheEntrate() {
		setCache(new HashMap<String, Entrate>());
		setCaricata(false);
	}

	public static synchronized CacheEntrate getSingleton() {

		if (singleton == null) {
			singleton = new CacheEntrate();
		}
		return singleton;
	}

	public Entrate getEntrate(final String id) {
		return getObjectById(entrateDAO, id);
	}

	public Map<String, Entrate> getAllEntrate() {
		return getAll(entrateDAO);
	}

	public List<Entrate> getAllEntrateForUtente() {
		final ArrayList<Entrate> listaEntrate = new ArrayList<>();
		final Map<String, Entrate> mappa = getAllEntrate();
		final Utenti utente = (Utenti) Controllore.getSingleton().getUtenteLogin();
		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				final Entrate entrata = (Entrate) mappa.get(chiavi.next());
				if (entrata != null && (entrata.getUtenti() != null && entrata.getUtenti().getidUtente() != 0)) {
					if (entrata.getUtenti().getidUtente() == utente.getidUtente()) {
						listaEntrate.add(entrata);
					}
				}
			}
		}
		return listaEntrate;
	}

	public List<Entrate> getAllEntrateForUtenteEAnno() {
		final ArrayList<Entrate> listaEntrate = new ArrayList<>();
		final Map<String, Entrate> mappa = getAllEntrate();
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
		final Map<String, Entrate> mappa = getAllEntrate();
		Optional<Entrate> max = mappa.values().stream().max((e1,e2) -> Integer.compare(e1.getidEntrate(), e2.getidEntrate()));
		return max.isPresent() ? max.get().getidEntrate() : 0;
	}

}
