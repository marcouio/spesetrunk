package com.molinari.gestionespese.business.comandi.singlespese;

import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.business.comandi.CommandUpdate;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;

import com.molinari.utility.graphic.component.alert.Alert;

public class CommandUpdateSpesa extends CommandUpdate<ISingleSpesa> {

	public CommandUpdateSpesa(final ISingleSpesa oldEntita, final ISingleSpesa newEntita) {
		super(oldEntita, newEntita, new WrapSingleSpesa());
		final CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public String toString() {
		return "Modificata Spesa " + getNewEntita().getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornata correttamente spesa " + getNewEntita().getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata spesa " + getOldEntita().getNome() + " precedentemente cancellata");
		}
	}

}
