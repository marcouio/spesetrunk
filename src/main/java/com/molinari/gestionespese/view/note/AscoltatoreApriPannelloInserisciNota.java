package com.molinari.gestionespese.view.note;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

public class AscoltatoreApriPannelloInserisciNota implements ActionListener {

	Finestra f = null;

	public AscoltatoreApriPannelloInserisciNota(Finestra frame) {
		f = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final NoteView dialog = new NoteView(new WrapNote(), f);
		dialog.setLocationRelativeTo(null);
		dialog.setBounds(0, 0, 350, 260);
		dialog.setVisible(true);
	}
}
