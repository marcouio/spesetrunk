package business.comandi;

import java.util.HashMap;

import business.Controllore;
import business.cache.CacheEntrate;
import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.Entrate;
import domain.wrapper.IWrapperEntity;

public class CommandInserisciEntrata extends AbstractCommand{

	
	final private AbstractOggettoEntita entita;
	final private IWrapperEntity wrap;
	private HashMap<String, AbstractOggettoEntita> mappaCache;
	
	public CommandInserisciEntrata(Entrate entita){
		CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = Controllore.getSingleton().getModel().getModelEntrate();
		this.entita = entita;
		
	}
	
	@Override
	public boolean execute() {
		if(entita instanceof Entrate){
			if(wrap.insert(entita)){
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if(entita instanceof Entrate){
			if(wrap.delete(Integer.parseInt(entita.getIdEntita()))){
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "Inserita Entrata " + ((Entrate)entita).getnome();
	}

}
