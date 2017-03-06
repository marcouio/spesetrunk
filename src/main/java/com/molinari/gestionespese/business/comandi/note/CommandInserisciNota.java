package com.molinari.gestionespese.business.comandi.note;

import com.molinari.gestionespese.business.cache.CacheNote;
import com.molinari.gestionespese.business.comandi.CommandInserisci;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

import grafica.componenti.alert.Alert;

public class CommandInserisciNota extends CommandInserisci<INote> {

	public CommandInserisciNota(final INote entita){
		super(entita, new WrapNote(), CacheNote.getSingleton());
	}

	@Override
	public String toString() {
		return "Inserita Nota " + entita.getNome();
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
