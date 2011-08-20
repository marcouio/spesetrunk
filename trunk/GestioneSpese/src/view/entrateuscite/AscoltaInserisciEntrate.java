package view.entrateuscite;

import java.awt.event.ActionEvent;

import view.Alert;
import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreEntrate;
import business.comandi.entrate.CommandInserisciEntrata;

public class AscoltaInserisciEntrate extends AscoltatoreAggiornatoreEntrate {

	private EntrateView view;

	public AscoltaInserisciEntrate(final EntrateView view) {
		this.view = view;
	}

	@Override
	public void actionPerformedOverride(final ActionEvent e) {
		view.setEntrate();

		if (view.nonEsistonoCampiNonValorizzati()) {
			if (Controllore.invocaComando(new CommandInserisciEntrata(view.getModelEntrate()))) {
				view.dispose();
			}
		} else {
			Alert.operazioniSegnalazioneErroreWarning("E' necessario riempire tutti i campi");
		}

	}

}
