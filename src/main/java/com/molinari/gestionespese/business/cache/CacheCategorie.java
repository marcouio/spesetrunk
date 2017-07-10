package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;

public class CacheCategorie extends AbstractCacheBase<ICatSpese> {

	private static CacheCategorie singleton;
	private final WrapCatSpese catSpeseDAO = new WrapCatSpese();

	private CacheCategorie() {
		setCache(new HashMap<String, ICatSpese>());
	}

	public static synchronized CacheCategorie getSingleton() {

		if (singleton == null) {
			singleton = new CacheCategorie();
		}
		return singleton;
	}

	public ICatSpese getCatSpese(final String id) {
		return getObjectById(catSpeseDAO, id);
	}

	public Map<String, ICatSpese> getAllCategorie() {
		return getAll(catSpeseDAO);
	}

	public List<ICatSpese> getListCategoriePerCombo() {
		final List<ICatSpese> categorie = new ArrayList<>();
		final ICatSpese categoria = new CatSpese();
		categoria.setnome("");
		categorie.add(0, categoria);
		
		categorie.addAll(getVettoreCategorie());
		return categorie;
	}

	public String[] getArrayCategorie(){
		final ArrayList<String> nomiCategorie = new ArrayList<>();
		
		List<ICatSpese> vettoreCategorie = getVettoreCategorie();
		for (ICatSpese catSpese : vettoreCategorie) {
			nomiCategorie.add(catSpese.getnome());
		}
		
		return nomiCategorie.toArray(new String[nomiCategorie.size()]);
	}

	public List<ICatSpese> getVettoreCategorie() {
		final Map<String, ICatSpese> mappa = this.getAllCategorie();
		Predicate<? super ICatSpese> predicate = getPredicateUtente();
		return mappa.values().stream().filter(predicate).collect(Collectors.toList());
	}

	private Predicate<? super ICatSpese> getPredicateUtente() {
		return c -> {
			int idUtenteGprs = c.getGruppi().getUtenti().getidUtente();
			int idUteLogin = ((IUtenti)Controllore.getUtenteLogin()).getidUtente();
			return idUtenteGprs == idUteLogin;
		};
	}
	
	public List<ICatSpese> getVettoreCategorieOld() {
		final List<ICatSpese> categorie = new ArrayList<>();
		final Map<String, ICatSpese> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		for (final Object element : lista) {
			categorie.add((ICatSpese) element);
		}
		return categorie;
	}

	public int getMaxId() {
		int maxId = 0;
		final Map<String, ICatSpese> mappa = getAllCategorie();
		final Iterator<String> chiavi = mappa.keySet().iterator();
		while (chiavi.hasNext()) {
			final ICatSpese categoria = mappa.get(chiavi.next());
			if (categoria != null) {
				final int idCategorie = categoria.getidCategoria();
				if (idCategorie > maxId) {
					maxId = idCategorie;
				}
			}
		}
		return maxId;
	}

}
