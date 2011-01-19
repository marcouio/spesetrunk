package view.componenti.movimenti;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class DialogHandler implements ActionListener {
	JDialog dia;

	public DialogHandler(JDialog dialog) {
		dia = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		dia.dispose();
	}

}