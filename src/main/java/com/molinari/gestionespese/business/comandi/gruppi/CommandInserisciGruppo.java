package com.molinari.gestionespese.business.comandi.gruppi;

import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.comandi.CommandInserisci;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;

import grafica.componenti.alert.Alert;

public class CommandInserisciGruppo extends CommandInserisci<IGruppi> {

	public CommandInserisciGruppo(final IGruppi entita) {
		super(entita, new WrapGruppi(), CacheGruppi.getSingleton());
	}

	@Override
	public String toString() {
		return "Inserito Gruppo " + entita.getNome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Inserito correttamente gruppo " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinato gruppo " + entita.getNome() + " precedentemente cancellato");
		}
	}

}
