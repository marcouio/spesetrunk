package com.molinari.gestionespese.business.comandi;

import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;
import db.dao.IDAO;

public class CommandUpdate<T extends AbstractOggettoEntita> extends AbstractCommandForJavaBean<T> {

	private final T newEntita;
	private final T oldEntita;

	public CommandUpdate(final T oldEntita, final T newEntita, final IDAO<T> wrap) {
		this.newEntita = newEntita;
		this.oldEntita = oldEntita;
		this.wrap = wrap;
	}

	@Override
	public boolean execute() throws Exception {
		return wrap.update(newEntita);
	}

	@Override
	public boolean unExecute() throws Exception {
		return wrap.update(oldEntita);
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		//do nothing
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		//do nothing
	}

	public T getNewEntita() {
		return newEntita;
	}

	public T getOldEntita() {
		return oldEntita;
	}

}
