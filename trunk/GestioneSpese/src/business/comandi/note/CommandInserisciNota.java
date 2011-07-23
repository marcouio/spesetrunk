package business.comandi.note;

import java.util.HashMap;

import view.Alert;
import business.cache.CacheNote;
import business.comandi.AbstractCommand;
import domain.AbstractOggettoEntita;
import domain.INote;
import domain.Note;
import domain.wrapper.IWrapperEntity;
import domain.wrapper.WrapNote;

public class CommandInserisciNota extends AbstractCommand {

	final private IWrapperEntity wrap;
	private final HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandInserisciNota(final INote entita) {
		final CacheNote cache = CacheNote.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapNote();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();

	}

	@Override
	public boolean execute() {
		if (entita instanceof Note) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (entita instanceof Note) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserita Nota " + ((Note) entita).getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Inserita correttamente nota " + entita.getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Cancellata nota " + entita.getnome() + " precedentemente inserita");
		}
	}
}
