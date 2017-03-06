package com.molinari.gestionespese.business.comandi.singlespese;

import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;

import command.javabeancommand.AbstractCommandForJavaBean;
import grafica.componenti.alert.Alert;

public class CommandInserisciSpesa extends AbstractCommandForJavaBean<SingleSpesa> {

	public CommandInserisciSpesa(ISingleSpesa entita) {
		final CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = cache.getCache();
		this.wrap = new WrapSingleSpesa();
		this.entita = (SingleSpesa) ((WrapSingleSpesa) entita).getEntitaPadre();
	}

	@Override
	public boolean execute() throws Exception {
		if (wrap.insert(entita)) {
			mappaCache.put(entita.getIdEntita(), entita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
			mappaCache.remove(entita.getIdEntita());
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserita Spesa " + entita.getnome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Inserita correttamente spesa " + entita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Cancellata spesa " + entita.getNome() + " precedentemente inserita");
		}
	}

}
