package com.molinari.gestionespese.business.comandi.singlespese;

import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.business.comandi.CommandInserisci;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;

import grafica.componenti.alert.Alert;

public class CommandInserisciSpesa extends CommandInserisci<ISingleSpesa> {

	public CommandInserisciSpesa(ISingleSpesa entita) {
		super(entita, new WrapSingleSpesa(), CacheUscite.getSingleton());
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
		return "Inserita Spesa " + entita.getNome();
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
