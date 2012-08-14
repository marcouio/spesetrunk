package business.comandi.note;

import java.util.HashMap;

import view.Alert;
import business.cache.CacheNote;
import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;
import db.dao.IDAO;
import domain.INote;
import domain.Note;
import domain.wrapper.WrapNote;

public class CommandInserisciNota extends AbstractCommandForJavaBean {

	private final HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandInserisciNota(final INote entita) throws Exception {
		final CacheNote cache = CacheNote.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapNote();
		this.entita = ((IDAO) entita).getEntitaPadre();

	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof Note) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
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
