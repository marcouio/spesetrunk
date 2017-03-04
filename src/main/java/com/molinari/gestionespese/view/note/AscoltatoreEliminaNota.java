package com.molinari.gestionespese.view.note;

import java.awt.event.ActionEvent;
import java.util.logging.Level;

import javax.swing.JFrame;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.comandi.note.CommandDeleteNota;
import com.molinari.gestionespese.domain.Note;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

import controller.ControlloreBase;
import grafica.componenti.alert.Alert;

public class AscoltatoreEliminaNota extends AscoltatoreAggiornatoreNiente {

	PannelloNota pNota = null;
	Note nota = null;

	public AscoltatoreEliminaNota(final PannelloNota pNota, final Note nota) {
		this.pNota = pNota;
		this.nota = nota;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		final WrapNote wn = new WrapNote(nota);
		try {
			Controllore.invocaComando(new CommandDeleteNota(wn));
		} catch (final Exception e1) {
			Alert.segnalazioneErroreGrave("Nota " + nota.getnome() + " non eliminata: " + e1.getMessage());
			ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
		}
		final JFrame f = pNota.getPadre();
		pNota.setVisible(false);
		f.remove(pNota);
		((MostraNoteView) f).aggiornaVista();
	}

}
