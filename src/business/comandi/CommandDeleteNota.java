package business.comandi;

import java.util.HashMap;

import business.cache.CacheNote;
import domain.AbstractOggettoEntita;
import domain.INote;
import domain.Note;
import domain.wrapper.IWrapperEntity;
import domain.wrapper.WrapNote;

public class CommandDeleteNota extends AbstractCommand {

	public CommandDeleteNota(INote entita) {
		CacheNote cache = CacheNote.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapNote();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();
	}

	@Override
	public boolean execute() {
		if (entita instanceof Note) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (entita instanceof Note) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Eliminata Nota " + ((Note) entita).getnome();
	}

}
