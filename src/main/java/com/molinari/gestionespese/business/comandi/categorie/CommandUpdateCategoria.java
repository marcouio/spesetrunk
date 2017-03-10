package com.molinari.gestionespese.business.comandi.categorie;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.CommandUpdate;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;

import command.ICommand;
import grafica.componenti.alert.Alert;

public class CommandUpdateCategoria extends CommandUpdate<ICatSpese> implements ICommand {

	public CommandUpdateCategoria(final ICatSpese oldEntita, final ICatSpese newEntita) {
		super(oldEntita, newEntita, new WrapCatSpese());
		final CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public String toString() {
		return "Modificata Categoria " + getNewEntita().getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornata correttamente categoria " + getNewEntita().getnome());
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata categoria " + getOldEntita().getnome() + " precedentemente aggiornata");
		}
	}
}
