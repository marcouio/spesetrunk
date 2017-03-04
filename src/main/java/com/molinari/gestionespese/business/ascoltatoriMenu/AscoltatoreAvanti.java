package com.molinari.gestionespese.business.ascoltatoriMenu;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.comandi.RedoCommand;

import command.CommandManager;

public class AscoltatoreAvanti extends AscoltatoreAggiornatoreTutto {

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		final CommandManager managerComandi = Controllore.getSingleton().getCommandManager();
		managerComandi.invocaComando(new RedoCommand());
	}
}
