package com.molinari.gestionespese.business.comandi.singlespese;

import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.business.comandi.CommandDelete;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;

import grafica.componenti.alert.Alert;

public class CommandDeleteSpesa extends CommandDelete<ISingleSpesa> {

	public CommandDeleteSpesa(ISingleSpesa entita) {
		super(entita, new WrapSingleSpesa(), CacheUscite.getSingleton());
	}
	@Override
	public String toString() {
		return "Eliminata Spesa " + entita.getNome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata correttamente spesa " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata spesa " + entita.getNome() + " precedentemente cancellata");
		}
	}

}
