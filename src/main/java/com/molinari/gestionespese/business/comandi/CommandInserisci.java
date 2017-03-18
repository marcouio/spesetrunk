package com.molinari.gestionespese.business.comandi;

import com.molinari.gestionespese.business.cache.AbstractCacheBase;

import com.molinari.utility.commands.beancommands.AbstractCommandForJavaBean;
import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;
import com.molinari.utility.database.dao.IDAO;

public class CommandInserisci<T extends AbstractOggettoEntita> extends AbstractCommandForJavaBean<T> {

	public CommandInserisci(final T entita, final IDAO<T> wrap, final AbstractCacheBase<T> cache) {
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
		//do nothing
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		//do nothing
	}

}
