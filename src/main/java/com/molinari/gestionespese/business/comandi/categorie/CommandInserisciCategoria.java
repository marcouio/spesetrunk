package com.molinari.gestionespese.business.comandi.categorie;

import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;
import grafica.componenti.alert.Alert;

public class CommandInserisciCategoria extends AbstractCommandForJavaBean<CatSpese> implements ICommand {

	public CommandInserisciCategoria(final ICatSpese entita) {
		final CacheCategorie cache = CacheCategorie.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapCatSpese();
		this.entita = (CatSpese) ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof CatSpese && wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof CatSpese && wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
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
