package business.ascoltatoriMenu;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Help;

public class AscoltatoreInfo implements ActionListener {

	final Help dialog = new Help();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		dialog.setSize(600, 440);
        dialog.setVisible(true);
        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
	}

}
