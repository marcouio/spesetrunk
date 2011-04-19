package business.comandi;

import java.util.HashMap;

import business.cache.CacheCategorie;
import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.ICatSpese;
import domain.wrapper.WrapCatSpese;

public class CommandUpdateCategoria extends AbstractCommand{

	final private CatSpese newEntita;
	final private CatSpese oldEntita;
	final private WrapCatSpese wrap;
	private HashMap<String, AbstractOggettoEntita> mappaCache;
	
	public CommandUpdateCategoria(CatSpese oldEntita,ICatSpese newEntita) {
		this.newEntita = (CatSpese) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapCatSpese();
		CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() {
		if(newEntita instanceof CatSpese){		
			if(wrap.update(newEntita)){
				mappaCache.put(Integer.toString(newEntita.getidCategoria()), newEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if(oldEntita instanceof CatSpese){
			if(wrap.update(oldEntita)){
				mappaCache.put(Integer.toString(oldEntita.getidCategoria()), oldEntita);
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "Modificata Categoria " + ((CatSpese)entita).getnome();
	}
}
