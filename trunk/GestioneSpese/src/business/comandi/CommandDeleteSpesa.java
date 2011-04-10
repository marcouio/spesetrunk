package business.comandi;

import java.util.HashMap;

import business.Controllore;
import business.cache.CacheUscite;
import domain.AbstractOggettoEntita;
import domain.SingleSpesa;

public class CommandDeleteSpesa extends AbstractCommand{

	public CommandDeleteSpesa(AbstractOggettoEntita entita) {
		CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = Controllore.getSingleton().getModel().getModelUscita();
		this.entita = entita;
	}
	
	@Override
	public boolean execute() {
		if(entita instanceof SingleSpesa){
			if(wrap.delete(Integer.parseInt(entita.getIdEntita()))){
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if(entita instanceof SingleSpesa){
			if(wrap.insert(entita)){
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "Eliminata Spesa " + ((SingleSpesa)entita).getnome();
	}

}
