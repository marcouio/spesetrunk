package com.molinari.gestionespese.view.entrateuscite;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreUscite;
import com.molinari.gestionespese.business.comandi.singlespese.CommandInserisciSpesa;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

public class AscoltaInserisciUscite extends AscoltatoreAggiornatoreUscite {

	private final AbstractUsciteView view;

	public AscoltaInserisciUscite(final AbstractUsciteView view) {
		this.view = view;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) {
		super.actionPerformedOverride(e);
		view.aggiornaModelDaVista();
		if (view.nonEsistonoCampiNonValorizzati()) {
			if (!Controllore.invocaComando(new CommandInserisciSpesa(view.getModelUscita()))) {
				final String msg = I18NManager.getSingleton().getMessaggio("insertcharges")+" "+ view.getModelUscita().getNome() + " "+I18NManager.getSingleton().getMessaggio("failed");
				Alert.segnalazioneErroreGrave(msg);
			}
		} else {
			Alert.info(I18NManager.getSingleton().getMessaggio("fillinall"), Alert.TITLE_ERROR);
		}
	}

}
