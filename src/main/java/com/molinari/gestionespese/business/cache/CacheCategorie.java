package com.molinari.gestionespese.business.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.GestioneSpeseException;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
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
		Optional<Collection<ICatSpese>> ofNullable = Optional.ofNullable(mappa.values());
		boolean present = ofNullable.isPresent();
		if(present) {
			return ofNullable.get().stream().filter(predicate).collect(Collectors.toList());
		}
		
		return new ArrayList<>();
	}
				

	private Predicate<? super ICatSpese> getPredicateUtente() {
		return c -> {
			IGruppi gruppi = c.getGruppi();
			if(gruppi == null || gruppi.getUtenti() == null) {
				throw new GestioneSpeseException("There are categories without linked group");
			}
			int idUtenteGprs = gruppi.getUtenti().getidUtente();
			int idUteLogin = ((IUtenti)Controllore.getUtenteLogin()).getidUtente();
			return idUtenteGprs == idUteLogin;
		};
	}
	
	public List<ICatSpese> getVettoreCategorieOld() {
		Collection<ICatSpese> values = this.getAllCategorie().values();
		if(values != null && !values.isEmpty()) {
			return new ArrayList<>(values);
		}
		return new ArrayList<>();
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
