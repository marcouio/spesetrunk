package business.comandi;

import java.util.HashMap;

import business.Controllore;
import business.cache.CacheEntrate;
import domain.AbstractOggettoEntita;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;

public class CommandUpdateSpesa extends AbstractCommand{

	final private SingleSpesa newEntita;
	final private SingleSpesa oldEntita;
	final private WrapSingleSpesa wrap;
	private HashMap<String, AbstractOggettoEntita> mappaCache;
	
	public CommandUpdateSpesa(SingleSpesa oldEntita,SingleSpesa newEntita) {
		this.newEntita = newEntita;
		this.oldEntita = oldEntita;
		this.wrap = Controllore.getSingleton().getModel().getModelUscita();
		CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() {
		if(newEntita instanceof SingleSpesa){		
			if(wrap.update(newEntita)){
				mappaCache.put(Integer.toString(newEntita.getidSpesa()), newEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if(oldEntita instanceof SingleSpesa){
			if(wrap.update(oldEntita)){
				mappaCache.put(Integer.toString(oldEntita.getidSpesa()), oldEntita);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Modificata Spesa " + ((SingleSpesa)entita).getnome();
	}
	
}
