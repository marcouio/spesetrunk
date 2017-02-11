package business.comandi.note;

import grafica.componenti.alert.Alert;
import business.cache.CacheNote;

import command.javabeancommand.AbstractCommandForJavaBean;

import db.dao.IDAO;
import domain.INote;
import domain.Note;
import domain.wrapper.WrapNote;

public class CommandInserisciNota extends AbstractCommandForJavaBean {

	public CommandInserisciNota(final INote entita) throws Exception {
		final CacheNote cache = CacheNote.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapNote();
		this.entita = ((IDAO) entita).getEntitaPadre();

	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof Note && wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof Note && wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserita Nota " + ((Note) entita).getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Inserita correttamente nota " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata nota " + entita.getNome() + " precedentemente inserita");
		}
	}
}
