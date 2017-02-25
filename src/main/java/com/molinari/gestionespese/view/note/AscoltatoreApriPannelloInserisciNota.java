package com.molinari.gestionespese.view.note;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.molinari.gestionespese.domain.wrapper.WrapNote;

public class AscoltatoreApriPannelloInserisciNota implements ActionListener {

	JFrame f = null;

	public AscoltatoreApriPannelloInserisciNota(JFrame frame) {
		f = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		NoteView dialog = new NoteView(new WrapNote(), f);
		dialog.setLocationRelativeTo(null);
		dialog.setBounds(0, 0, 350, 260);
		dialog.setVisible(true);
	}
}
