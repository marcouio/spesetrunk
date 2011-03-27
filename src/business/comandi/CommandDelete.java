package business.comandi;

import java.util.HashMap;

import business.cache.AbstractCacheBase;
import domain.AbstractOggettoEntita;
import domain.wrapper.IWrapperEntity;

public class CommandDelete extends AbstractCommand{

	public CommandDelete(AbstractOggettoEntita entita, IWrapperEntity wrap, AbstractCacheBase cache) {
		this.entita = entita;
		this.wrap = wrap;
		this.mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}
	
	@Override
	public void execute() {
		if(wrap.delete(Integer.parseInt(entita.getIdEntita()))){
			mappaCache.remove(entita.getIdEntita());
		}
		super.execute();
	}

	@Override
	public void unExecute() {
		if(wrap.insert(entita)){
			mappaCache.put(entita.getIdEntita(), entita);
		}
		super.unExecute();
	}

}
