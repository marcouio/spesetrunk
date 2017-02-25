package com.molinari.gestionespese.business.comandi.gruppi;

import grafica.componenti.alert.Alert;

import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;

public class CommandDeleteGruppo extends AbstractCommandForJavaBean<Gruppi> implements ICommand{

	public CommandDeleteGruppo(final IGruppi entita) {
		final CacheGruppi cache = CacheGruppi.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapGruppi();
		this.entita = (Gruppi) ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof Gruppi && wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof Gruppi && wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Eliminato Gruppo " + ((Gruppi) entita).getNome();
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