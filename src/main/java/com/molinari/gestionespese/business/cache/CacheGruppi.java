package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;

public class CacheGruppi extends AbstractCacheBase<IGruppi> {

	private static CacheGruppi singleton;
	private final WrapGruppi gruppiDAO = new WrapGruppi();

	private CacheGruppi() {
		setCache(new HashMap<String, IGruppi>());
	}

	public static CacheGruppi getSingleton() {
		if (singleton == null) {
			singleton = new CacheGruppi();
		}
		return singleton;
	}

	public IGruppi getGruppo(final String id) {
		return getObjectById(gruppiDAO, id);
	}

	public IGruppi getGruppoPerNome(final String nome) {
		IGruppi gruppo = getCache().get(nome);
		if (gruppo == null) {
			gruppo = caricaGruppoPerNome(nome);
			if (gruppo != null) {
				getCache().put(Integer.toString(gruppo.getidGruppo()), gruppo);
			}
		}
		return gruppo;
	}

	private IGruppi caricaGruppoPerNome(final String nome) {
		return new WrapGruppi().selectByNome(nome);
	}

	public Map<String, IGruppi> getAllGruppi() {
		return getAll(gruppiDAO);
	}

	public List<IGruppi> getVettoreGruppiSenzaZero() {
		final List<IGruppi> gruppi = new ArrayList<>();
		final Map<String, IGruppi> mappa = this.getAllGruppi();
		final Object[] lista = mappa.values().toArray();
		for (final Object element : lista) {
			final IGruppi gruppo = (IGruppi) element;
			if (gruppo != null && gruppo.getnome() != null) {
				gruppi.add((IGruppi) element);
			}
		}
		return gruppi;
	}

	public List<IGruppi> getVettoreGruppi() {
		final List<IGruppi> gruppi = new ArrayList<>();
		final Map<String, IGruppi> mappa = this.getAllGruppi();
		final Object[] lista = mappa.values().toArray();
		for (int i = lista.length - 1; i >= 0; i--) {
			gruppi.add((IGruppi) lista[i]);
		}
		return gruppi;
	}

	public List<IGruppi> getListCategoriePerCombo(final Map<String, IGruppi> mappa) {
		final List<IGruppi> gruppi = new ArrayList<>();
		final Object[] lista = mappa.values().toArray();
		final IGruppi gruppo = new Gruppi();
		gruppo.setnome("");
		for (final Object element : lista) {
			gruppi.add((IGruppi) element);
		}
		gruppi.add(0, gruppo);
		return gruppi;
	}

	public int getMaxId() {
		int maxId = 0;
		final Map<String, IGruppi> mappa = getAllGruppi();
		final Iterator<String> chiavi = mappa.keySet().iterator();
		while (chiavi.hasNext()) {
			final IGruppi gruppo = mappa.get(chiavi.next());
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
