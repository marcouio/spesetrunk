package view.note;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import view.Alert;
import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import business.comandi.note.CommandDeleteNota;
import domain.Note;
import domain.wrapper.WrapNote;

public class AscoltatoreEliminaNota extends AscoltatoreAggiornatoreNiente {

	PannelloNota pNota = null;
	Note nota = null;

	public AscoltatoreEliminaNota(final PannelloNota pNota, final Note nota) {
		this.pNota = pNota;
		this.nota = nota;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		final WrapNote wn = new WrapNote(nota);
		try {
			Controllore.invocaComando(new CommandDeleteNota(wn));
		} catch (final Exception e1) {
			Alert.operazioniSegnalazioneErroreGrave("Nota " + nota.getnome() + " non eliminata: " + e1.getMessage());
		}
		final JFrame f = pNota.getPadre();
		pNota.setVisible(false);
		f.remove(pNota);
		((MostraNoteView) f).aggiornaVista();
	}

}
