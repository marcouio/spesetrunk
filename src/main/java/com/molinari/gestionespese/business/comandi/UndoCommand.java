package com.molinari.gestionespese.business.comandi;

import com.molinari.utility.commands.AbstractCommand;

public class UndoCommand extends AbstractCommand {

	@Override
	public String toString() {
		return "Effettuato comando 'Indietro'";
	}
}
