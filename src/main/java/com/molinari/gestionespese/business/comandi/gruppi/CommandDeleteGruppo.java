package com.molinari.gestionespese.business.comandi.gruppi;

import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.comandi.CommandDelete;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.utility.commands.ICommand;
import com.molinari.utility.graphic.component.alert.Alert;

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
			Alert.segnalazioneInfo("Cancellata correttamente entrata " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata categoria " + entita.getNome() + " precedentemente cancellata");
		}
	}
}
