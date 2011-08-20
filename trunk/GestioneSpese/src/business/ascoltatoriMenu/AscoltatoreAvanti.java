package business.ascoltatoriMenu;

import java.awt.event.ActionEvent;

import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.comandi.CommandManager;
import business.comandi.RedoCommand;

public class AscoltatoreAvanti extends AscoltatoreAggiornatoreTutto {

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		final CommandManager managerComandi = Controllore.getSingleton().getCommandManager();
		managerComandi.invocaComando(new RedoCommand());
	}
}