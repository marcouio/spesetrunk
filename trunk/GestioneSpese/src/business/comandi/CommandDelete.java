package business.comandi;

import java.util.HashMap;

import business.cache.AbstractCacheBase;
import domain.AbstractOggettoEntita;
import domain.wrapper.IWrapperEntity;

public class CommandDelete extends AbstractCommand {

	public CommandDelete(final AbstractOggettoEntita entita, final IWrapperEntity wrap, final AbstractCacheBase cache) {
		this.entita = entita;
		this.wrap = wrap;
		this.mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() {
		if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean unExecute() {
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
