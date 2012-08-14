package business.ascoltatoriMenu;

import java.awt.event.ActionEvent;

import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.comandi.UndoCommand;

import command.CommandManager;

public class AscoltatoreIndietro extends AscoltatoreAggiornatoreTutto {

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		final CommandManager managerComandi = Controllore.getSingleton().getCommandManager();
		managerComandi.invocaComando(new UndoCommand());
	}

}
