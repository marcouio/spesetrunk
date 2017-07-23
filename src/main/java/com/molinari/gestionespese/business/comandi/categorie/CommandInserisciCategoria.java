package com.molinari.gestionespese.business.comandi.categorie;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.CommandInserisci;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;
import com.molinari.utility.commands.ICommand;
import com.molinari.utility.graphic.component.alert.Alert;

public class CommandInserisciCategoria extends CommandInserisci<ICatSpese> implements ICommand {

	public CommandInserisciCategoria(final ICatSpese entita) {
		super(entita, new WrapCatSpese(), CacheCategorie.getSingleton());
	}

	@Override
	public String toString() {
		return "Inserita Categoria " + entita.getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Categoria " + entita.getNome() + " inserita correttamente");
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Eliminata categoria " + entita.getNome() + " precedentemente inserita");
		}
	}

}
