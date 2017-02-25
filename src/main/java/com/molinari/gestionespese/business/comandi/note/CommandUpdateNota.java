package com.molinari.gestionespese.business.comandi.note;

import grafica.componenti.alert.Alert;

import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.Note;

import command.javabeancommand.AbstractCommandForJavaBean;

public class CommandUpdateNota extends AbstractCommandForJavaBean<Note> {

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
		if (newEntita instanceof Note && wrap.update(newEntita)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (oldEntita instanceof Note && wrap.update(oldEntita)) {
			return true;
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
