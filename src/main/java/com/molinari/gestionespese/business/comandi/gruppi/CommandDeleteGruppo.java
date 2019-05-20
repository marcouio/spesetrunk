package com.molinari.gestionespese.business.comandi.gruppi;

import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.comandi.CommandDelete;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.utility.commands.ICommand;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

public class CommandDeleteGruppo extends CommandDelete<IGruppi> implements ICommand{

	public CommandDeleteGruppo(final IGruppi entita) {
		super(entita, new WrapGruppi(), CacheGruppi.getSingleton());
	}

	@Override
	public String toString() {
		return "Eliminato Gruppo " + entita.getNome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo(I18NManager.getSingleton().getMessaggio("deletegroupsuccess", entita.getNome()));
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo(I18NManager.getSingleton().getMessaggio("deletegroupsuccess", entita.getNome()));
		}
	}
}
