package business.comandi.note;

import grafica.componenti.alert.Alert;

import java.util.HashMap;

import business.cache.CacheNote;

import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;

import domain.INote;
import domain.Note;
import domain.wrapper.WrapNote;

public class CommandDeleteNota extends AbstractCommandForJavaBean {

	public CommandDeleteNota(INote entita) throws Exception {
		CacheNote cache = CacheNote.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapNote();
		this.entita = ((WrapNote) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws NumberFormatException, Exception {
		if (entita instanceof Note) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
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

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata correttamente nota " + entita.getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata nota " + entita.getnome() + " precedentemente cancellata");
		}
	}

}
