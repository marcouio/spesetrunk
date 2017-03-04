package com.molinari.gestionespese.business.comandi;

import command.AbstractCommand;

public class UndoCommand extends AbstractCommand {

	@Override
	public String toString() {
		return "Effettuato comando 'Indietro'";
	}
}
