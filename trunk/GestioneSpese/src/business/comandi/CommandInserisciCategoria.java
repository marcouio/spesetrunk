package business.comandi;

import java.util.HashMap;

import business.Controllore;
import business.cache.CacheCategorie;
import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.wrapper.IWrapperEntity;

public class CommandInserisciCategoria extends AbstractCommand{

	
	final private AbstractOggettoEntita entita;
	final private IWrapperEntity wrap;
	private HashMap<String, AbstractOggettoEntita> mappaCache;
	
	public CommandInserisciCategoria(CatSpese entita){
		CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = Controllore.getSingleton().getModel().getModelCategorie();
		this.entita = entita;
	}
	
	@Override
	public void execute() {
		super.execute();
		if(entita instanceof CatSpese){
			if(wrap.insert(entita)){
				mappaCache.put(entita.getIdEntita(), entita);
			}
		}
	}

	@Override
	public void unExecute() {
		super.unExecute();
		if(entita instanceof CatSpese){
			if(wrap.delete(Integer.parseInt(entita.getIdEntita()))){
				mappaCache.remove(entita.getIdEntita());
			}
		}
	}
	

}
