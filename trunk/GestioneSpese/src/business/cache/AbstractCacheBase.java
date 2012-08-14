package business.cache;

import java.util.HashMap;

import command.javabeancommand.AbstractOggettoEntita;

public abstract class AbstractCacheBase {

	protected HashMap<String, AbstractOggettoEntita> cache;
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
	
	public HashMap<String, AbstractOggettoEntita> getCache() {
		return cache;
	}

	public void setCache(HashMap<String, AbstractOggettoEntita> cache) {
		this.cache = cache;
	}

}
