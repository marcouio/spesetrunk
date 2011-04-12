package business.ascoltatoriMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import business.Controllore;
import business.comandi.CommandManager;
import business.comandi.RedoCommand;

public class AscoltatoreAvanti implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		CommandManager managerComandi =  Controllore.getSingleton().getCommandManager();
		managerComandi.invocaComando(new RedoCommand(),"tutto");
	}
}
