package com.molinari.gestionespese.view.entrateuscite;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreEntrate;
import com.molinari.gestionespese.business.comandi.entrate.CommandInserisciEntrata;
import com.molinari.utility.messages.I18NManager;

import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltaInserisciEntrate extends AscoltatoreAggiornatoreEntrate {

	private final EntrateView view;

	public AscoltaInserisciEntrate(final EntrateView view) {
		this.view = view;
	}

	@Override
	public void actionPerformedOverride(final ActionEvent e) {
		view.aggiornaModelDaVista();

		if (view.nonEsistonoCampiNonValorizzati()) {
			if (Controllore.invocaComando(new CommandInserisciEntrata(view.getModelEntrate()))) {
				view.getDialog().dispose();
			}
		} else {
			Alert.segnalazioneErroreWarning(I18NManager.getSingleton().getMessaggio("fillinall"));
		}

	}

}
