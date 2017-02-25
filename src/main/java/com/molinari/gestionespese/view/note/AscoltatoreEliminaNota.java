package com.molinari.gestionespese.view.note;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.comandi.note.CommandDeleteNota;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

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
			Alert.segnalazioneErroreGrave("Nota " + nota.getnome() + " non eliminata: " + e1.getMessage());
		}
		final JFrame f = pNota.getPadre();
		pNota.setVisible(false);
		f.remove(pNota);
		((MostraNoteView) f).aggiornaVista();
	}

}
