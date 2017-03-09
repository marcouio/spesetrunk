package com.molinari.gestionespese.business.comandi.gruppi;

import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.comandi.CommandUpdate;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;

import grafica.componenti.alert.Alert;

public class CommandUpdateGruppo extends CommandUpdate<IGruppi> {

	public CommandUpdateGruppo(final IGruppi oldEntita, final IGruppi newEntita) {
		super(oldEntita, newEntita, new WrapGruppi());
		final CacheGruppi cache = CacheGruppi.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public boolean execute() throws Exception {
		if(super.execute()){
			mappaCache.put(Integer.toString(getNewEntita().getidGruppo()), getNewEntita());
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if(super.unExecute()){
			mappaCache.put(Integer.toString(getOldEntita().getidGruppo()), getOldEntita());
			return true;
			
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Gruppo " + getNewEntita().getnome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornato correttamente gruppo " + getNewEntita().getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinato gruppo " + getOldEntita().getnome() + " precedentemente aggiornato");
		}
	}
}
