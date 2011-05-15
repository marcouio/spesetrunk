package business.comandi;

import java.util.HashMap;

import business.cache.CacheNote;
import domain.AbstractOggettoEntita;
import domain.INote;
import domain.Note;
import domain.wrapper.WrapNote;

public class CommandUpdateNota extends AbstractCommand {

	final private Note     newEntita;
	final private Note     oldEntita;
	final private WrapNote wrap;

	public CommandUpdateNota(Note oldEntita, INote newEntita) {
		this.newEntita = (Note) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapNote();
		CacheNote cache = CacheNote.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() {
		if (newEntita instanceof Note) {
			if (wrap.update(newEntita)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (oldEntita instanceof Note) {
			if (wrap.update(oldEntita)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Nota " + (newEntita).getnome();
	}
}
