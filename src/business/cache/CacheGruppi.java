package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class CacheGruppi extends AbstractCacheBase<Gruppi> {

	private static CacheGruppi singleton;
	private WrapGruppi gruppiDAO = new WrapGruppi();

	private CacheGruppi() {
		setCache(new HashMap<String, Gruppi>());
	}

	public static CacheGruppi getSingleton() {
		if (singleton == null) {
			singleton = new CacheGruppi();
		}
		return singleton;
	}

	public Gruppi getGruppo(final String id) {
		return getObjectById(gruppiDAO, id);
	}

	public Gruppi getGruppoPerNome(final String nome) {
		Gruppi gruppo = (Gruppi) getCache().get(nome);
		if (gruppo == null) {
			gruppo = caricaGruppoPerNome(nome);
			if (gruppo != null) {
				getCache().put(Integer.toString(gruppo.getidGruppo()), gruppo);
			}
		}
		return gruppo;
	}

	private Gruppi caricaGruppoPerNome(final String nome) {
		return new WrapGruppi().selectByNome(nome);
	}

	public Map<String, Gruppi> getAllGruppi() {
		return getAll(gruppiDAO);
	}

	public List<Gruppi> getVettoreGruppiSenzaZero() {
		final List<Gruppi> gruppi = new ArrayList<>();
		final Map<String, Gruppi> mappa = this.getAllGruppi();
		final Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			final Gruppi gruppo = (Gruppi) lista[i];
			if (gruppo != null && gruppo.getnome() != null) {
				gruppi.add((Gruppi) lista[i]);
			}
		}
		return gruppi;
	}

	public List<Gruppi> getVettoreGruppi() {
		final List<Gruppi> gruppi = new ArrayList<>();
		final Map<String, Gruppi> mappa = this.getAllGruppi();
		final Object[] lista = mappa.values().toArray();
		for (int i = lista.length - 1; i >= 0; i--) {
			gruppi.add((Gruppi) lista[i]);
		}
		return gruppi;
	}

	public List<Gruppi> getListCategoriePerCombo(final Map<String, Gruppi> mappa) {
		final List<Gruppi> gruppi = new ArrayList<>();
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
		final Map<String, Gruppi> mappa = getAllGruppi();
		final Iterator<String> chiavi = mappa.keySet().iterator();
		while (chiavi.hasNext()) {
			final Gruppi gruppo = (Gruppi) mappa.get(chiavi.next());
			if (gruppo != null) {
				final int idGruppo = gruppo.getidGruppo();
				if (idGruppo > maxId) {
					maxId = idGruppo;
				}
			}
		}
		return maxId;
	}

}
