package com.molinari.gestionespese.business.menulistener;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.utility.commands.CommandManager;
import com.molinari.utility.commands.RedoCommand;
import com.molinari.utility.controller.ControlloreBase;

public class AscoltatoreAvanti extends AscoltatoreAggiornatoreTutto {

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		final CommandManager managerComandi = ControlloreBase.getSingleton().getCommandManager();
		managerComandi.invocaComando(new RedoCommand());
	}
}
