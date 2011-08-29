package business.ascoltatoriMenu;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class AscoltatoreCreaDialog implements ActionListener{

	private JDialog dialog;
	private int larghezza = 385;
	private int altezza = 300;
	
	public AscoltatoreCreaDialog(JDialog dialog) {
		this.dialog = dialog;
	}
	
	public AscoltatoreCreaDialog(JDialog dialog, int width, int height) {
		this.dialog = dialog;
		this.larghezza = width;
		this.altezza = height;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.setSize(larghezza, altezza);
        dialog.setVisible(true);
        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
		
	}
	
	
}
