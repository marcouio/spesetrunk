package business.comandi;

import java.util.HashMap;

import business.Controllore;
import business.cache.CacheEntrate;
import domain.AbstractOggettoEntita;
import domain.CatSpese;

public class CommandDeleteCategoria extends AbstractCommand{

	public CommandDeleteCategoria(AbstractOggettoEntita entita) {
		CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = Controllore.getSingleton().getModel().getModelEntrate();
		this.entita = entita;
	}
	
	@Override
	public void execute() {
		if(entita instanceof CatSpese){
			if(wrap.delete(Integer.parseInt(entita.getIdEntita()))){
				mappaCache.remove(entita.getIdEntita());
			}
		}
		super.execute();
	}

	@Override
	public void unExecute() {
		if(entita instanceof CatSpese){
			if(wrap.insert(entita)){
				mappaCache.put(entita.getIdEntita(), entita);
			}
		}
		super.unExecute();
	}

}
