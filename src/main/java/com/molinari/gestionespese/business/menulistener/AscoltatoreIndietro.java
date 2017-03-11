package com.molinari.gestionespese.business.menulistener;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.comandi.UndoCommand;

import command.CommandManager;

public class AscoltatoreIndietro extends AscoltatoreAggiornatoreTutto {

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		final CommandManager managerComandi = Controllore.getSingleton().getCommandManager();
		managerComandi.invocaComando(new UndoCommand());
	}

}
