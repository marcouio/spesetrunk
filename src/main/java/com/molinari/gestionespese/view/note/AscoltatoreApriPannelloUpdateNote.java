package com.molinari.gestionespese.view.note;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

public class AscoltatoreApriPannelloUpdateNote implements ActionListener {

	Finestra   f    = null;
	WrapNote note = null;

	public AscoltatoreApriPannelloUpdateNote(final Finestra f, final WrapNote note) {
		this.f = f;
		this.note = note;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final NoteView dialog = new NoteView(note, f);
		dialog.getDialog().setLocationRelativeTo(null);
		dialog.getDialog().setBounds(0, 0, 350, 260);
		dialog.getDialog().setVisible(true);
		dialog.setNota(note.getnome());
		dialog.setTaDescrizione(note.getDescrizione());
		dialog.settfData(note.getData());
		dialog.getBtnInserisci().setText("Aggiorna");
		dialog.getBtnInserisci().setActionCommand("Aggiorna");

	}
}
