package com.molinari.gestionespese.business.comandi.singlespese;

import com.molinari.gestionespese.business.cache.CacheUscite;
import com.molinari.gestionespese.domain.ISingleSpesa;
import com.molinari.gestionespese.domain.SingleSpesa;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;

import command.javabeancommand.AbstractCommandForJavaBean;
import grafica.componenti.alert.Alert;

public class CommandUpdateSpesa extends AbstractCommandForJavaBean<SingleSpesa> {

	private final SingleSpesa newEntita;
	private final SingleSpesa oldEntita;
	private final WrapSingleSpesa wrap;

	public CommandUpdateSpesa(final SingleSpesa oldEntita, final ISingleSpesa newEntita) {
		this.newEntita = (SingleSpesa) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapSingleSpesa();
		final CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public boolean execute() {
		if (wrap.update(newEntita)) {
			mappaCache.put(Integer.toString(newEntita.getidSpesa()), newEntita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (wrap.update(oldEntita)) {
			mappaCache.put(Integer.toString(oldEntita.getidSpesa()), oldEntita);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Spesa " + newEntita.getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornata correttamente spesa " + newEntita.getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata spesa " + oldEntita.getnome() + " precedentemente cancellata");
		}
	}

}
