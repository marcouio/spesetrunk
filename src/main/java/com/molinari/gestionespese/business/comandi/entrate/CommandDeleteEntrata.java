package com.molinari.gestionespese.business.comandi.entrate;

import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.business.comandi.CommandDelete;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;

import com.molinari.utility.commands.ICommand;
import com.molinari.utility.graphic.component.alert.Alert;

public class CommandDeleteEntrata extends CommandDelete<IEntrate> implements ICommand {

	public CommandDeleteEntrata(final IEntrate entita) {
		super(entita, new WrapEntrate(), CacheEntrate.getSingleton());
	}

	@Override
	public String toString() {
		return "Eliminata Entrata " + entita.getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ok, entrata" + entita.getNome() + " eliminata correttamente!");
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ok, ripristinata entrata" + entita.getNome() + " precedentemente eliminata!");
		}
	}

}
