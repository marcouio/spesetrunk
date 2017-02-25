package com.molinari.gestionespese.business.comandi;

import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;
import db.dao.IDAO;

public class CommandUpdate extends AbstractCommandForJavaBean {

	final private AbstractOggettoEntita newEntita;
	final private AbstractOggettoEntita oldEntita;

	public CommandUpdate(final AbstractOggettoEntita oldEntita, final AbstractOggettoEntita newEntita, final IDAO wrap) {
		this.newEntita = newEntita;
		this.oldEntita = oldEntita;
		this.wrap = wrap;
	}

	@Override
	public boolean execute() throws Exception {
		if (wrap.update(newEntita)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean unExecute() throws Exception {
		if (wrap.update(oldEntita)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {

	}

}
