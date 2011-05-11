package business.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.wrapper.WrapCatSpese;

public class CacheCategorie extends AbstractCacheBase {

	private static CacheCategorie singleton;

	private CacheCategorie() {
		cache = new HashMap<String, AbstractOggettoEntita>();
	}

	public static CacheCategorie getSingleton() {
		if (singleton == null) {
			synchronized (CacheCategorie.class) {
				if (singleton == null) {
					singleton = new CacheCategorie();
				}
			} // if
		} // if
		return singleton;
	}

	WrapCatSpese catSpeseDAO = new WrapCatSpese();

	public CatSpese getCatSpese(String id) {
		CatSpese categoria = (CatSpese) cache.get(id);
		if (categoria == null) {
			categoria = caricaCategoria(id);
			if (categoria != null) {
				cache.put(id, categoria);
			}
		}
		return (CatSpese) cache.get(id);
	}

	private CatSpese caricaCategoria(String id) {
		CatSpese categorie = (CatSpese) new WrapCatSpese().selectById(Integer.parseInt(id));
		return categorie;
	}

	private Map<String, AbstractOggettoEntita> chargeAllCategorie() {
		Vector<Object> categorie = catSpeseDAO.selectAll();
		if (categorie != null && categorie.size() > 0) {
			for (int i = 0; i < categorie.size(); i++) {
				CatSpese categoria = (CatSpese) categorie.get(i);
				int id = categoria.getidCategoria();
				if (cache.get(id) == null) {
					cache.put(Integer.toString(id), categoria);
				}
			}
		}
		caricata = true;
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllCategorie() {
		if (!caricata)
			cache = chargeAllCategorie();
		return cache;
	}

	public Vector<CatSpese> getVettoreCategoriePerCombo(Map<String, AbstractOggettoEntita> mappa) {
		Vector<CatSpese> categorie = new Vector<CatSpese>();
		Object[] lista = mappa.values().toArray();
		CatSpese categoria = new CatSpese();
		categoria.setnome("");
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		categorie.add(0, categoria);
		return categorie;
	}

	public Vector<CatSpese> getVettoreCategoriePerCombo() {
		Vector<CatSpese> categorie = new Vector<CatSpese>();
		Map<String, AbstractOggettoEntita> mappa = this.getAllCategorie();
		Object[] lista = mappa.values().toArray();
		CatSpese categoria = new CatSpese();
		categoria.setnome("");
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		categorie.add(0, categoria);
		return categorie;
	}

	public Vector<CatSpese> getCategorieSenzaGruppo() {
		Vector<CatSpese> allCategorie = getVettoreCategorie();
		Vector<CatSpese> catSenzaGruppo = new Vector<CatSpese>();
		for (int i = 0; i < allCategorie.size(); i++) {
			CatSpese categoria = allCategorie.get(i);
			if (categoria.getGruppi() == null || categoria.getGruppi().getidGruppo() == 0) {
				catSenzaGruppo.add(categoria);
			}
		}
		return catSenzaGruppo;
	}

	public Vector<CatSpese> getVettoreCategorie(Map<String, CatSpese> mappa) {
		Vector<CatSpese> categorie = new Vector<CatSpese>();
		Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		return categorie;
	}

	public Vector<CatSpese> getVettoreCategorie() {
		Vector<CatSpese> categorie = new Vector<CatSpese>();
		Map<String, AbstractOggettoEntita> mappa = this.getAllCategorie();
		Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		return categorie;
	}

	public Object[] arrayCategorie() {
		Map<String, AbstractOggettoEntita> mappa = this.getAllCategorie();
		return mappa.values().toArray();
	}

}
