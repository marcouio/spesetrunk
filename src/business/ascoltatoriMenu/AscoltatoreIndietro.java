package business.ascoltatoriMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import business.Controllore;
import business.comandi.CommandManager;
import business.comandi.UndoCommand;

public class AscoltatoreIndietro implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		CommandManager managerComandi = Controllore.getSingleton().getCommandManager();
		managerComandi.invocaComando(new UndoCommand(), "tutto");
	}

}
