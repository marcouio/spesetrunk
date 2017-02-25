package com.molinari.gestionespese.business.cache;

import java.util.List;
import java.util.Map;

import command.javabeancommand.AbstractOggettoEntita;
import db.dao.IDAO;

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
	
	public T getObjectById(IDAO dao, String id){
		T obj = cache.get(id);
		if (obj == null) {
			obj = caricaObj(dao, id);
			if (obj != null) {
				cache.put(id, obj);
			}
		}
		return cache.get(id);
	}
	
	public Map<String, T> chargeAllObject(IDAO dao) {
		try {
			final List<T> objs = (List<T>) dao.selectAll();
			if (objs != null && !objs.isEmpty()) {
				for (int i = 0; i < objs.size(); i++) {
					final T object = objs.get(i);
					final String id = object.getIdEntita();
					if (cache.get(id) == null) {
						cache.put(id, object);
					}
				}
			}
			caricata = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cache;
	}
	
	public Map<String, T> getAll(IDAO dao) {
		if (caricata)
			return cache;
		else
			return chargeAllObject(dao);
	}

	private T caricaObj(IDAO dao, String id) {
		try {
			return (T) dao.selectById(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
