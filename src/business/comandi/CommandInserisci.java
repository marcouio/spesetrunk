package business.comandi;

import business.cache.AbstractCacheBase;
import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;
import db.dao.IDAO;

public class CommandInserisci extends AbstractCommandForJavaBean {

	public CommandInserisci(final AbstractOggettoEntita entita, final IDAO wrap, final AbstractCacheBase cache) {
		mappaCache = cache.getCache();
		this.wrap = wrap;
		this.entita = entita;
	}

	@Override
	public boolean execute() throws Exception {
		if (wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean unExecute() throws Exception {
		if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {

	}

}
