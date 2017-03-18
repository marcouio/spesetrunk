package com.molinari.gestionespese.business.comandi;

import com.molinari.gestionespese.business.cache.AbstractCacheBase;

import com.molinari.utility.commands.beancommands.AbstractCommandForJavaBean;
import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;
import com.molinari.utility.database.dao.IDAO;

public class CommandDelete<T extends AbstractOggettoEntita> extends AbstractCommandForJavaBean<T> {

	public CommandDelete(final T entita, final IDAO<T> wrap, final AbstractCacheBase<T> cache) {
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
		//do nothing
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		//do nothing
	}

}
