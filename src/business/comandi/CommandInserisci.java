package business.comandi;

import java.util.HashMap;

import business.cache.AbstractCacheBase;
import domain.AbstractOggettoEntita;
import domain.wrapper.IWrapperEntity;

public class CommandInserisci extends AbstractCommand {

	public CommandInserisci(final AbstractOggettoEntita entita, final IWrapperEntity wrap, final AbstractCacheBase cache) {
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = wrap;
		this.entita = entita;
	}

	@Override
	public boolean execute() {
		if (wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean unExecute() {
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
