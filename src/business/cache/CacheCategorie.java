package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import command.javabeancommand.AbstractOggettoEntita;
import domain.CatSpese;
import domain.wrapper.WrapCatSpese;

public class CacheCategorie extends AbstractCacheBase<CatSpese> {

	private static CacheCategorie singleton;
	private WrapCatSpese catSpeseDAO = new WrapCatSpese();
	
	private CacheCategorie() {
		cache = new HashMap<String, CatSpese>();
	}

	public static synchronized CacheCategorie getSingleton() {

		if (singleton == null) {
			singleton = new CacheCategorie();
		}
		return singleton;
	}

	public CatSpese getCatSpese(final String id) {
		CatSpese categoria = (CatSpese) cache.get(id);
		if (categoria == null) {
			categoria = caricaCategoria(id);
			if (categoria != null) {
				cache.put(id, categoria);
			}
		}
		return (CatSpese) cache.get(id);
	}

	private CatSpese caricaCategoria(final String id) {
		return (CatSpese) new WrapCatSpese().selectById(Integer.parseInt(id));
	}

	private Map<String, CatSpese> chargeAllCategorie() {
		final List<Object> categorie = catSpeseDAO.selectAll();
		if (categorie != null && !categorie.isEmpty()) {
			for (int i = 0; i < categorie.size(); i++) {
				final CatSpese categoria = (CatSpese) categorie.get(i);
				final int id = categoria.getidCategoria();
				if (cache.get(id) == null) {
					cache.put(Integer.toString(id), categoria);
				}
			}
		}
		caricata = true;
		return cache;
	}

	public Map<String, CatSpese> getAllCategorie() {
		if (!caricata) {
			cache = chargeAllCategorie();
		}
		return cache;
	}

	public List<CatSpese> getListCategoriePerCombo(final Map<String, CatSpese> mappa) {
		final List<CatSpese> categorie = new ArrayList<>();
		final Object[] lista = mappa.values().toArray();
		final CatSpese categoria = new CatSpese();
		categoria.setnome("");
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		categorie.add(0, categoria);
		return categorie;
	}

	public List<CatSpese> getListCategoriePerCombo() {
		final List<CatSpese> categorie = new ArrayList<>();
		final Map<String, CatSpese> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		final CatSpese categoria = new CatSpese();
		categoria.setnome("");
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		categorie.add(0, categoria);
		return categorie;
	}

	public List<CatSpese> getCategorieSenzaGruppo() {
		final List<CatSpese> allCategorie = getVettoreCategorie();
		final List<CatSpese> catSenzaGruppo = new ArrayList<CatSpese>();
		for (int i = 0; i < allCategorie.size(); i++) {
			final CatSpese categoria = allCategorie.get(i);
			if (categoria.getGruppi() == null ) {
				catSenzaGruppo.add(categoria);
			}
		}
		return catSenzaGruppo;
	}

	public List<CatSpese> getVettoreCategorie(final Map<String, CatSpese> mappa) {
		final List<CatSpese> categorie = new ArrayList<CatSpese>();
		final Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		return categorie;
	}

	public String[] getArrayCategorie(){
		final ArrayList<String> nomiCategorie = new ArrayList<String>();
		final Map<String, CatSpese> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			nomiCategorie.add(((CatSpese) lista[i]).getnome());
		}
		return nomiCategorie.toArray(new String[nomiCategorie.size()]);
	}

	public List<CatSpese> getVettoreCategorie() {
		final List<CatSpese> categorie = new ArrayList<>();
		final Map<String, CatSpese> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		return categorie;
	}

	public Object[] arrayCategorie() {
		final Map<String, CatSpese> mappa = this.getAllCategorie();
		return mappa.values().toArray();
	}

	public int getMaxId() {
		int maxId = 0;
		final Map<String, CatSpese> mappa = getAllCategorie();
		final Iterator<String> chiavi = mappa.keySet().iterator();
		while (chiavi.hasNext()) {
			final CatSpese categoria = (CatSpese) mappa.get(chiavi.next());
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
