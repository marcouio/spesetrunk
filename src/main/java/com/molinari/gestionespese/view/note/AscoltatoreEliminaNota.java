package com.molinari.gestionespese.view.note;

import java.awt.event.ActionEvent;
import java.util.logging.Level;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.Finestra;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreNiente;
import com.molinari.gestionespese.business.comandi.note.CommandDeleteNota;
import com.molinari.gestionespese.domain.INote;
import com.molinari.gestionespese.domain.wrapper.WrapNote;

import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltatoreEliminaNota extends AscoltatoreAggiornatoreNiente {

	PannelloNota pNota = null;
	INote nota = null;

	public AscoltatoreEliminaNota(final PannelloNota pNota, final INote nota) {
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
			Alert.segnalazioneErroreGrave("Nota " + nota.getNome() + " non eliminata: " + e1.getMessage());
			ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
		}
		final Finestra f = pNota.getPadre();
		pNota.setVisible(false);
		f.getContainer().remove(pNota);
		((MostraNoteView) f).aggiornaVista();
	}

}
