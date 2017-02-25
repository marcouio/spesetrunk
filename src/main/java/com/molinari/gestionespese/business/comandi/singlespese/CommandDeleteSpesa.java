package com.molinari.gestionespese.business.comandi.singlespese;

import grafica.componenti.alert.Alert;

import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;

import command.javabeancommand.AbstractCommandForJavaBean;
import db.dao.IDAO;

public class CommandDeleteSpesa extends AbstractCommandForJavaBean<SingleSpesa> {

	public CommandDeleteSpesa(ISingleSpesa entita) {
		CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapSingleSpesa();
		this.entita = (SingleSpesa) ((IDAO) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof SingleSpesa && wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof SingleSpesa && wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Eliminata Spesa " + ((SingleSpesa) entita).getnome();
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
