package com.molinari.gestionespese.business.comandi.categorie;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import grafica.componenti.alert.Alert;

public class CommandUpdateCategoria extends AbstractCommandForJavaBean<CatSpese> implements ICommand {

	private final CatSpese newEntita;
	private final CatSpese oldEntita;
	private final WrapCatSpese wrap;

	public CommandUpdateCategoria(final CatSpese oldEntita, final ICatSpese newEntita) {
		this.newEntita = (CatSpese) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapCatSpese();
		final CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public boolean execute() {
		if (newEntita instanceof CatSpese && wrap.update(newEntita)) {
			mappaCache.put(Integer.toString(newEntita.getidCategoria()), newEntita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (oldEntita instanceof CatSpese && wrap.update(oldEntita)) {
			mappaCache.put(Integer.toString(oldEntita.getidCategoria()), oldEntita);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Categoria " + newEntita.getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornata correttamente categoria " + newEntita.getnome());
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata categoria " + oldEntita.getnome() + " precedentemente aggiornata");
		}
	}
}
