package business.comandi;

import java.util.HashMap;

import business.Controllore;
import business.cache.CacheEntrate;
import domain.AbstractOggettoEntita;
import domain.Entrate;
import domain.wrapper.WrapEntrate;

public class CommandUpdateEntrata extends AbstractCommand{

	final private Entrate newEntita;
	final private Entrate oldEntita;
	final private WrapEntrate wrap;
	
	public CommandUpdateEntrata(Entrate oldEntita,Entrate newEntita) {
		this.newEntita = newEntita;
		this.oldEntita = oldEntita;
		this.wrap = Controllore.getSingleton().getModel().getModelEntrate();
		CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() {
		if(newEntita instanceof Entrate){		
			if(wrap.update(newEntita)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if(oldEntita instanceof Entrate){
			if(wrap.update(oldEntita)){
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "Modificata Entrata " + ((Entrate)entita).getnome();
	}
}
