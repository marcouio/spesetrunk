package view.note;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import domain.wrapper.WrapNote;

public class AscoltatoreApriPannelloUpdateNote implements ActionListener {

	JFrame   f    = null;
	WrapNote note = null;

	public AscoltatoreApriPannelloUpdateNote(final JFrame f, final WrapNote note) {
		this.f = f;
		this.note = note;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		NoteView dialog = new NoteView(note, f);
		dialog.setLocationRelativeTo(null);
		dialog.setBounds(0, 0, 350, 260);
		dialog.setVisible(true);
		dialog.setNota(note.getnome());
		dialog.setTaDescrizione(note.getDescrizione());
		dialog.settfData(note.getData());
		dialog.getBtnInserisci().setText("Aggiorna");
		dialog.getBtnInserisci().setActionCommand("Aggiorna");

	}
}
