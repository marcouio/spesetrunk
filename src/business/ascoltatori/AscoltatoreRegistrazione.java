package business.ascoltatori;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.login.Registrazione;

public class AscoltatoreRegistrazione implements ActionListener {

	final Registrazione dialog = new Registrazione();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		dialog.setSize(385, 300);
        dialog.setVisible(true);
        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
	}

}
