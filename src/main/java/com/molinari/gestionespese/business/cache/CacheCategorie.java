package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
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

	public List<ICatSpese> getListCategoriePerCombo(final Map<String, ICatSpese> mappa) {
		final List<ICatSpese> categorie = new ArrayList<>();
		final Object[] lista = mappa.values().toArray();
		final ICatSpese categoria = new CatSpese();
		categoria.setnome("");
		for (final Object element : lista) {
			categorie.add((ICatSpese) element);
		}
		categorie.add(0, categoria);
		return categorie;
	}

	public List<ICatSpese> getListCategoriePerCombo() {
		final List<ICatSpese> categorie = new ArrayList<>();
		final Map<String, ICatSpese> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		final ICatSpese categoria = new CatSpese();
		categoria.setnome("");
		for (final Object element : lista) {
			categorie.add((ICatSpese) element);
		}
		categorie.add(0, categoria);
		return categorie;
	}

	public List<ICatSpese> getCategorieSenzaGruppo() {
		final List<ICatSpese> allCategorie = getVettoreCategorie();
		final List<ICatSpese> catSenzaGruppo = new ArrayList<>();
		for (int i = 0; i < allCategorie.size(); i++) {
			final ICatSpese categoria = allCategorie.get(i);
			if (categoria.getGruppi() == null ) {
				catSenzaGruppo.add(categoria);
			}
		}
		return catSenzaGruppo;
	}

	public List<ICatSpese> getVettoreCategorie(final Map<String, ICatSpese> mappa) {
		final List<ICatSpese> categorie = new ArrayList<>();
		final Object[] lista = mappa.values().toArray();
		for (final Object element : lista) {
			categorie.add((ICatSpese) element);
		}
		return categorie;
	}

	public String[] getArrayCategorie(){
		final ArrayList<String> nomiCategorie = new ArrayList<>();
		final Map<String, ICatSpese> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		for (final Object element : lista) {
			nomiCategorie.add(((ICatSpese) element).getnome());
		}
		return nomiCategorie.toArray(new String[nomiCategorie.size()]);
	}

	public List<ICatSpese> getVettoreCategorie() {
		final List<ICatSpese> categorie = new ArrayList<>();
		final Map<String, ICatSpese> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		for (final Object element : lista) {
			categorie.add((ICatSpese) element);
		}
		return categorie;
	}

	public Object[] arrayCategorie() {
		final Map<String, ICatSpese> mappa = this.getAllCategorie();
		return mappa.values().toArray();
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
