package com.molinari.gestionespese.business.comandi.note;

import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.business.comandi.CommandUpdate;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

import grafica.componenti.alert.Alert;

public class CommandUpdateNota extends CommandUpdate<INote> {

	public CommandUpdateNota(final Note oldEntita, final INote newEntita) {
		super(oldEntita, newEntita, new WrapNote());
		final CacheNote cache = CacheNote.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public String toString() {
		return "Modificata Nota " + getNewEntita().getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornata correttamente nota " + getNewEntita().getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata nota " + getOldEntita().getNome() + " precedentemente aggiornata");
		}
	}
}
