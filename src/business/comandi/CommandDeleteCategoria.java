package business.comandi;

import java.util.HashMap;

import business.cache.CacheCategorie;
import domain.AbstractOggettoEntita;
import domain.CatSpese;
import domain.ICatSpese;
import domain.wrapper.IWrapperEntity;
import domain.wrapper.WrapCatSpese;

public class CommandDeleteCategoria extends AbstractCommand{

	public CommandDeleteCategoria(ICatSpese entita) {
		CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapCatSpese();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();
	}
	
	@Override
	public boolean execute() {
		if(entita instanceof CatSpese){
			if(wrap.delete(Integer.parseInt(entita.getIdEntita()))){
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if(entita instanceof CatSpese){
			if(wrap.insert(entita)){
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		return "Eliminata Categoria " + ((CatSpese)entita).getnome();
	}
}