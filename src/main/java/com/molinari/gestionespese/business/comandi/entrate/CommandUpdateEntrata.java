package com.molinari.gestionespese.business.comandi.entrate;

import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.business.comandi.CommandUpdate;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;

import command.ICommand;
import grafica.componenti.alert.Alert;

public class CommandUpdateEntrata extends CommandUpdate<IEntrate> implements ICommand {

	public CommandUpdateEntrata(final IEntrate oldEntita, final IEntrate newEntita) {
		super(oldEntita, newEntita, new WrapEntrate());
		final CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public String toString() {
		return "Modificata Entrata " + getNewEntita().getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornata correttamente entrata " + getNewEntita().getnome());
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata categoria " + getOldEntita().getnome() + " precedentemente aggiornata");
		}
	}
}
