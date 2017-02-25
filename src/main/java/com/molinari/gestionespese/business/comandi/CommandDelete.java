package com.molinari.gestionespese.business.comandi;

import com.molinari.gestionespese.business.cache.AbstractCacheBase;

import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;
import db.dao.IDAO;

public class CommandDelete<T extends AbstractOggettoEntita> extends AbstractCommandForJavaBean<T> {

	public CommandDelete(final T entita, final IDAO wrap, final AbstractCacheBase<T> cache) {
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
