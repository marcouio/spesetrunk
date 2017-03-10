package com.molinari.gestionespese.business.comandi.note;

import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.business.comandi.CommandDelete;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

import grafica.componenti.alert.Alert;

public class CommandDeleteNota extends CommandDelete<INote> {

	public CommandDeleteNota(INote entita) {
		super(entita, new WrapNote(), CacheNote.getSingleton());
	}

	@Override
	public String toString() {
		return "Eliminata Nota " + entita.getNome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata correttamente nota " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata nota " + entita.getNome() + " precedentemente cancellata");
		}
	}

}
