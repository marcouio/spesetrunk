package com.molinari.gestionespese.business.comandi.note;

import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.Note;

import command.javabeancommand.AbstractCommandForJavaBean;
import grafica.componenti.alert.Alert;

public class CommandUpdateNota extends AbstractCommandForJavaBean<INote> {

	private final Note newEntita;
	private final Note oldEntita;

	public CommandUpdateNota(final Note oldEntita, final INote newEntita) {
		this.newEntita = (Note) newEntita;
		this.oldEntita = oldEntita;
		final CacheNote cache = CacheNote.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public boolean execute() throws Exception {
		if (wrap.update(newEntita)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (wrap.update(oldEntita)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Nota " + newEntita.getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornata correttamente nota " + newEntita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata nota " + oldEntita.getNome() + " precedentemente aggiornata");
		}
	}
}
