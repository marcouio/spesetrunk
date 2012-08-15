package view.entrateuscite;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreEntrate;
import business.comandi.entrate.CommandInserisciEntrata;
import business.internazionalizzazione.I18NManager;

public class AscoltaInserisciEntrate extends AscoltatoreAggiornatoreEntrate {

	private EntrateView view;

	public AscoltaInserisciEntrate(final EntrateView view) {
		this.view = view;
	}

	@Override
	public void actionPerformedOverride(final ActionEvent e) throws Exception {
		view.aggiornaModelDaVista();

		if (view.nonEsistonoCampiNonValorizzati()) {
			if (Controllore.invocaComando(new CommandInserisciEntrata(view.getModelEntrate()))) {
				view.dispose();
			}
		} else {
			Alert.segnalazioneErroreWarning(I18NManager.getSingleton().getMessaggio("fillinall"));
		}

	}

}
