package com.molinari.gestionespese.business.comandi.entrate;

import com.molinari.gestionespese.business.cache.CacheEntrate;
import com.molinari.gestionespese.domain.Entrate;
import com.molinari.gestionespese.domain.IEntrate;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import grafica.componenti.alert.Alert;

public class CommandUpdateEntrata extends AbstractCommandForJavaBean<Entrate> implements ICommand {

	private final Entrate newEntita;
	private final Entrate oldEntita;
	private final WrapEntrate wrap;

	public CommandUpdateEntrata(final Entrate oldEntita, final IEntrate newEntita) {
		this.newEntita = (Entrate) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapEntrate();
		final CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public boolean execute() {
		if (newEntita instanceof Entrate && wrap.update(newEntita)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (oldEntita instanceof Entrate && wrap.update(oldEntita)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Entrata " + newEntita.getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornata correttamente entrata " + newEntita.getnome());
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata categoria " + oldEntita.getnome() + " precedentemente aggiornata");
		}
	}
}
