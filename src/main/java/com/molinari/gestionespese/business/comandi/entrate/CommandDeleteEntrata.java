package com.molinari.gestionespese.business.comandi.entrate;

import grafica.componenti.alert.Alert;

import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;

public class CommandDeleteEntrata extends AbstractCommandForJavaBean<Entrate> implements ICommand {

	public CommandDeleteEntrata(final IEntrate entita) {
		final CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapEntrate();
		this.entita = (Entrate) ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof Entrate && wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof Entrate && wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Eliminata Entrata " + ((Entrate) entita).getNome();
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