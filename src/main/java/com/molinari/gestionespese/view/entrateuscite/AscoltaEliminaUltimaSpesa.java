package com.molinari.gestionespese.view.entrateuscite;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreUscite;
import com.molinari.gestionespese.business.comandi.singlespese.CommandDeleteSpesa;
import com.molinari.utility.graphic.component.alert.Alert;

public final class AscoltaEliminaUltimaSpesa extends AscoltatoreAggiornatoreUscite {
	/**
	 * 
	 */
	private final AbstractUsciteView usciteView;

	/**
	 * @param usciteView
	 */
	public AscoltaEliminaUltimaSpesa(AbstractUsciteView usciteView) {
		this.usciteView = usciteView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		try {
			Controllore.invocaComando(new CommandDeleteSpesa(this.usciteView.getModelUscita()));
		} catch (final Exception e1) {
			Alert.segnalazioneEccezione(e1,"Cancellazione della spesa " + this.usciteView.getModelUscita().getNome() + " non riuscita: " + e1.getMessage());
		}
	}
}