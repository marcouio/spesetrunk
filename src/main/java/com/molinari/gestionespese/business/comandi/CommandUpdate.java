package com.molinari.gestionespese.business.comandi;

import com.molinari.utility.commands.beancommands.AbstractCommandForJavaBean;
import com.molinari.utility.commands.beancommands.AbstractOggettoEntita;
import com.molinari.utility.database.dao.IDAO;

public class CommandUpdate<T extends AbstractOggettoEntita> extends AbstractCommandForJavaBean<T> {

	private final T newEntita;
	private final T oldEntita;

	public CommandUpdate(final T oldEntita, final T newEntita, final IDAO<T> wrap) {
		this.newEntita = newEntita;
		this.oldEntita = oldEntita;
		this.wrap = wrap;
	}

	@Override
	public boolean execute() {
		return wrap.update(newEntita);
	}

	@Override
	public boolean unExecute() {
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
