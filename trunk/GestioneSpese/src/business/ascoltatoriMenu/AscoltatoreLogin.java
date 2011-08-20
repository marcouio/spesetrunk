package business.ascoltatoriMenu;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.login.Login;

public class AscoltatoreLogin implements ActionListener {

	final Login dialog = new Login();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		dialog.setSize(400, 220);
        dialog.setVisible(true);
        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
	}

}
