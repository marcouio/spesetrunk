package business.comandi;

import java.util.HashMap;

import business.Controllore;
import business.cache.CacheUscite;
import domain.AbstractOggettoEntita;
import domain.SingleSpesa;
import domain.wrapper.IWrapperEntity;

public class CommandInserisciSpesa extends AbstractCommand{

	
	final private AbstractOggettoEntita entita;
	final private IWrapperEntity wrap;
	private HashMap<String, AbstractOggettoEntita> mappaCache;
	
	public CommandInserisciSpesa(SingleSpesa entita){
		CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = Controllore.getSingleton().getModel().getModelUscita();
		this.entita = entita;
	}
	
	@Override
	public void execute() {
		super.execute();
		if(entita instanceof SingleSpesa){
			if(wrap.insert(entita)){
				mappaCache.put(entita.getIdEntita(), entita);
			}
		}
	}

	@Override
	public void unExecute() {
		super.unExecute();
		if(entita instanceof SingleSpesa){
			if(wrap.delete(Integer.parseInt(entita.getIdEntita()))){
				mappaCache.remove(entita.getIdEntita());
			}
		}
	}
	

}
