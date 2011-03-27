package business.cache;

import java.util.Map;

import domain.AbstractOggettoEntita;

public abstract class AbstractCacheBase {

	protected Map<String, AbstractOggettoEntita> cache;
	protected boolean caricata = false;
	
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
	
	public Map<String, AbstractOggettoEntita> getCache() {
		return cache;
	}

	public void setCache(Map<String, AbstractOggettoEntita> cache) {
		this.cache = cache;
	}

}
