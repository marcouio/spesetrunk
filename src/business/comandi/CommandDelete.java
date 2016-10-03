package business.comandi;

import command.javabeancommand.AbstractCommandForJavaBean;

import business.cache.AbstractCacheBase;
import command.javabeancommand.AbstractOggettoEntita;
import db.dao.IDAO;

public class CommandDelete extends AbstractCommandForJavaBean {

	public CommandDelete(final AbstractOggettoEntita entita, final IDAO wrap, final AbstractCacheBase cache) {
		this.entita = entita;
		this.wrap = wrap;
		this.mappaCache = cache.getCache();
	}

	@Override
	public boolean execute() throws Exception {
		if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean unExecute() throws Exception {
		if (wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
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
