package com.molinari.gestionespese.business.ascoltatoriMenu;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.molinari.gestionespese.view.Help;

public class AscoltatoreInfo implements ActionListener {

	final Help dialog = new Help();

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.setBounds(400, 200, 600, 400);
		dialog.setVisible(true);
		dialog.setModalityType(ModalityType.APPLICATION_MODAL);
	}

}
