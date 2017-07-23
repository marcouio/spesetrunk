package com.molinari.gestionespese.business.comandi.entrate;

import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.business.comandi.CommandInserisci;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.utility.commands.ICommand;
import com.molinari.utility.graphic.component.alert.Alert;

public class CommandInserisciEntrata extends CommandInserisci<IEntrate> implements ICommand {

	public CommandInserisciEntrata(final IEntrate entita) {
		super(entita, new WrapEntrate(), CacheEntrate.getSingleton());
	}

	@Override
	public String toString() {
		return "Inserita Entrata " + entita.getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Entrata " + entita.getNome() + " inserita correttamente");
		}else{
			Alert.getMessaggioErrore("Entrata " + entita.getNome() + "non inserita: verifica i log");
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Eliminata entrata " + entita.getNome() + " inserita precedentemente");
		}else{
			Alert.getMessaggioErrore("Impossibile eliminare l'entrata " + entita.getNome() + " inserita precedentemente: verificare i log");
		}
	}

}
