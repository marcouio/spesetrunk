package business.comandi;

import java.util.HashMap;

import business.cache.CacheUscite;
import domain.AbstractOggettoEntita;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.wrapper.IWrapperEntity;
import domain.wrapper.WrapSingleSpesa;

public class CommandInserisciSpesa extends AbstractCommand{

	
	final private AbstractOggettoEntita entita;
	final private IWrapperEntity wrap;
	private HashMap<String, AbstractOggettoEntita> mappaCache;
	
	public CommandInserisciSpesa(ISingleSpesa entita){
		CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapSingleSpesa();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();
	}
	
	@Override
	public boolean execute() {
		if(entita instanceof SingleSpesa){
			if(wrap.insert(entita)){
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if(entita instanceof SingleSpesa){
			if(wrap.delete(Integer.parseInt(entita.getIdEntita()))){
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "Inserita Spesa " + ((SingleSpesa)entita).getnome();
	}

}
