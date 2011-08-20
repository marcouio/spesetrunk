package view.entrateuscite;

import java.awt.event.ActionEvent;

import view.Alert;
import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreUscite;
import business.comandi.singlespese.CommandInserisciSpesa;

public class AscoltaInserisciUscite extends AscoltatoreAggiornatoreUscite {

	private UsciteView view;

	public AscoltaInserisciUscite(final UsciteView view) {
		this.view = view;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) {
		super.actionPerformedOverride(e);
		view.setUscite();
		if (view.nonEsistonoCampiNonValorizzati()) {
			if (!Controllore.invocaComando(new CommandInserisciSpesa(view.getModelUscita()))) {
				Alert.operazioniSegnalazioneErroreGrave("Inserimento spesa " + view.getModelUscita().getnome() + "non riusciuta");
			}
		} else {
			Alert.info("E' necessario riempire tutti i campi", Alert.TITLE_ERROR);
		}
	}

}
