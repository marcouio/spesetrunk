package business.comandi.note;

import grafica.componenti.alert.Alert;

import java.util.HashMap;

import business.cache.CacheNote;

import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;

import domain.INote;
import domain.Note;

public class CommandUpdateNota extends AbstractCommandForJavaBean {

	final private Note newEntita;
	final private Note oldEntita;

	public CommandUpdateNota(final Note oldEntita, final INote newEntita) {
		this.newEntita = (Note) newEntita;
		this.oldEntita = oldEntita;
		final CacheNote cache = CacheNote.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() throws Exception {
		if (newEntita instanceof Note) {
			if (wrap.update(newEntita)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
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
			Alert.segnalazioneInfo("Aggiornata correttamente nota " + newEntita.getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata nota " + oldEntita.getnome() + " precedentemente aggiornata");
		}
	}
}
