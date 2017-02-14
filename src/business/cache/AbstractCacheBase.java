package business.cache;

import java.util.Map;

import command.javabeancommand.AbstractOggettoEntita;

public abstract class AbstractCacheBase<T extends AbstractOggettoEntita> {

	protected Map<String, T> cache;
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
	
	public Map<String, T> getCache() {
		return cache;
	}

	public void setCache(Map<String, T> cache) {
		this.cache = cache;
	}

}
