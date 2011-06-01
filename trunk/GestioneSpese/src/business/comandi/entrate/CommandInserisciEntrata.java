package business.comandi.entrate;

import java.util.HashMap;

import business.cache.CacheEntrate;
import business.comandi.AbstractCommand;
import domain.AbstractOggettoEntita;
import domain.Entrate;
import domain.IEntrate;
import domain.wrapper.IWrapperEntity;
import domain.wrapper.WrapEntrate;

public class CommandInserisciEntrata extends AbstractCommand{

	
	final private IWrapperEntity wrap;
	private HashMap<String, AbstractOggettoEntita> mappaCache;
	
	public CommandInserisciEntrata(IEntrate entita){
		CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapEntrate();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();
		
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
