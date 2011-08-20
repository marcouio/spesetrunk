package business.comandi.note;

import java.util.HashMap;

import view.Alert;
import business.cache.CacheNote;
import business.comandi.AbstractCommand;
import domain.AbstractOggettoEntita;
import domain.INote;
import domain.Note;
import domain.wrapper.WrapNote;

public class CommandUpdateNota extends AbstractCommand {

	final private Note newEntita;
	final private Note oldEntita;
	final private WrapNote wrap;

	public CommandUpdateNota(final Note oldEntita, final INote newEntita) {
		this.newEntita = (Note) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapNote();
		final CacheNote cache = CacheNote.getSingleton();
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

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Aggiornata correttamente nota " + newEntita.getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Ripristinata nota " + oldEntita.getnome() + " precedentemente aggiornata");
		}
	}
}
