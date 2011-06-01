package view.note;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import business.Controllore;
import business.comandi.note.CommandDeleteNota;
import domain.Note;
import domain.wrapper.WrapNote;

public class AscoltatoreEliminaNota implements ActionListener {

	PannelloNota pNota = null;
	Note         nota  = null;

	public AscoltatoreEliminaNota(final PannelloNota pNota, final Note nota) {
		this.pNota = pNota;
		this.nota = nota;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		WrapNote wn = new WrapNote(nota);
		Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteNota(wn), null);
		JFrame f = pNota.getPadre();
		pNota.setVisible(false);
		f.remove(pNota);
		((MostraNoteView) f).aggiornaVista();
	}

}
