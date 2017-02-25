package com.molinari.gestionespese.business.comandi;

import command.AbstractCommand;

public class UndoCommand extends AbstractCommand {

	@Override
	public boolean execute() {
		return true;
	}

	@Override
	public boolean unExecute() {
		return true;
	}

	@Override
	public String toString() {
		return "Effettuato comando 'Indietro'";
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {

	}

}
