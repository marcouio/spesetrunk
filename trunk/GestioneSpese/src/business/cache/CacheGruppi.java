package business.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import domain.AbstractOggettoEntita;
import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class CacheGruppi extends AbstractCacheBase {

	private static CacheGruppi singleton;

	private CacheGruppi() {
		cache = new HashMap<String, AbstractOggettoEntita>();
	}

	public static CacheGruppi getSingleton() {
		if (singleton == null) {
			synchronized (CacheGruppi.class) {
				if (singleton == null) {
					singleton = new CacheGruppi();
				}
			} // if
		} // if
		return singleton;
	}

	WrapGruppi gruppiDAO = new WrapGruppi();

	public Gruppi getGruppo(final String id) {
		Gruppi gruppo = (Gruppi) cache.get(id);
		if (gruppo == null) {
			gruppo = caricaGruppo(id);
			if (gruppo != null) {
				cache.put(id, gruppo);
			}
		}
		return (Gruppi) cache.get(id);
	}

	public Gruppi getGruppoPerNome(final String nome) {
		Gruppi gruppo = (Gruppi) cache.get(nome);
		if (gruppo == null) {
			gruppo = caricaGruppoPerNome(nome);
			if (gruppo != null) {
				cache.put(Integer.toString(gruppo.getidGruppo()), gruppo);
			}
		}
		return gruppo;
	}

	private Gruppi caricaGruppoPerNome(final String nome) {
		return new WrapGruppi().selectByNome(nome);
	}

	private Gruppi caricaGruppo(final String id) {
		return (Gruppi) new WrapGruppi().selectById(Integer.parseInt(id));
	}

	public Map<String, AbstractOggettoEntita> chargeAllGruppi() {
		final Vector<Object> gruppi = gruppiDAO.selectAll();
		if (gruppi != null && gruppi.size() > 0) {
			for (int i = 0; i < gruppi.size(); i++) {
				final Gruppi gruppo = (Gruppi) gruppi.get(i);
				final int id = gruppo.getidGruppo();
				if (cache.get(id) == null) {
					cache.put(Integer.toString(id), gruppo);
				}
			}
			caricata = true;
		}
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllGruppi() {
		if (caricata) {
			return cache;
		} else {
			return chargeAllGruppi();
		}
	}

	public Vector<Gruppi> getVettoreGruppiSenzaZero() {
		final Vector<Gruppi> gruppi = new Vector<Gruppi>();
		final Map<String, AbstractOggettoEntita> mappa = this.getAllGruppi();
		final Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			final Gruppi gruppo = (Gruppi) lista[i];
			if (gruppo != null && gruppo.getnome() != null) {
				if (!gruppo.getnome().equals("No Gruppo")) {
					gruppi.add((Gruppi) lista[i]);
				}
			}
		}
		return gruppi;
	}

	public Vector<Gruppi> getVettoreGruppi() {
		final Vector<Gruppi> gruppi = new Vector<Gruppi>();
		final Map<String, AbstractOggettoEntita> mappa = this.getAllGruppi();
		final Object[] lista = mappa.values().toArray();
		for (int i = lista.length - 1; i >= 0; i--) {
			gruppi.add((Gruppi) lista[i]);
		}
		return gruppi;
	}

	public Vector<Gruppi> getVettoreCategoriePerCombo(final Map<String, AbstractOggettoEntita> mappa) {
		final Vector<Gruppi> gruppi = new Vector<Gruppi>();
		final Object[] lista = mappa.values().toArray();
		final Gruppi gruppo = new Gruppi();
		gruppo.setnome("");
		for (int i = 0; i < lista.length; i++) {
			gruppi.add((Gruppi) lista[i]);
		}
		gruppi.add(0, gruppo);
		return gruppi;
	}

	public int getMaxId() {
		int maxId = 0;
		final Map<String, AbstractOggettoEntita> mappa = getAllGruppi();
		final Iterator<String> chiavi = mappa.keySet().iterator();
		if (mappa != null) {
			while (chiavi.hasNext()) {
				final Gruppi gruppo = (Gruppi) mappa.get(chiavi.next());
				if (gruppo != null) {
					final int idGruppo = gruppo.getidGruppo();
					if (idGruppo > maxId) {
						maxId = idGruppo;
					}
				}
			}
		}
		return maxId;
	}

}
