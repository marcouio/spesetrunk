package com.molinari.gestionespese.business.cache;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.database.dao.IDAO;

public abstract class AbstractCacheBase<T extends AbstractOggettoEntita> {

	private Map<String, T> cache;
	private boolean caricata = false;

	/**
	 * @return the caricata
	 */
	public boolean isCaricata() {
		return caricata;
	}

	/**
	 * @param caricata the caricata to set
	 */
	public void setCaricata(boolean caricata) {
		this.caricata = caricata;
	}

	public Map<String, T> getCache() {
		return cache;
	}

	public void setCache(Map<String, T> cache) {
		this.cache = cache;
	}

	public T getObjectById(IDAO<T> dao, String id){
		T obj = cache.get(id);
		if (obj == null) {
			obj = caricaObj(dao, id);
			if (obj != null) {
				cache.put(id, obj);
			}
		}
		return cache.get(id);
	}

	public Map<String, T> chargeAllObject(IDAO<T> dao) {
		try {
			final List<T> objs = dao.selectAll();
			if (objs != null && !objs.isEmpty()) {
				for (int i = 0; i < objs.size(); i++) {
					final T object = objs.get(i);
					final String id = object.getIdEntita();
					putInCache(object, id);
				}
			}
			caricata = true;
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return cache;
	}

	private void putInCache(final T object, final String id) {
		if (cache.get(id) == null) {
			cache.put(id, object);
		}
	}

	public Map<String, T> getAll(IDAO<T> dao) {
		if (caricata) {
			return cache;
		} else {
			return chargeAllObject(dao);
		}
	}

	private T caricaObj(IDAO<T> dao, String id) {
		try {
			return dao.selectById(Integer.parseInt(id));
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

}
