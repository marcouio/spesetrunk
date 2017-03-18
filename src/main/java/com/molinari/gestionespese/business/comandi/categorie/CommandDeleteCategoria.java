package com.molinari.gestionespese.business.comandi.categorie;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.CommandDelete;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;

import com.molinari.utility.commands.ICommand;
import com.molinari.utility.graphic.component.alert.Alert;

public class CommandDeleteCategoria extends CommandDelete<ICatSpese> implements ICommand {

	public CommandDeleteCategoria(ICatSpese entita) {
		super(entita, new WrapCatSpese(), CacheCategorie.getSingleton());
	}

	@Override
	public String toString() {
		return "Eliminata Categoria " + ((CatSpese) entita).getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata correttamente la categoria: " + entita.getNome());
		}
	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristina la categoria: " + entita.getNome() + " precedentemente cancellata");
		}
	}
}
