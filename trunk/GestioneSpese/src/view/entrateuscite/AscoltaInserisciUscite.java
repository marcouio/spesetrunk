package view.entrateuscite;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreUscite;
import business.comandi.singlespese.CommandInserisciSpesa;
import business.internazionalizzazione.I18NManager;

public class AscoltaInserisciUscite extends AscoltatoreAggiornatoreUscite {

	private UsciteView view;

	public AscoltaInserisciUscite(final UsciteView view) {
		this.view = view;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		view.aggiornaModelDaVista();
		if (view.nonEsistonoCampiNonValorizzati()) {
			if (!Controllore.invocaComando(new CommandInserisciSpesa(view.getModelUscita()))) {
				String msg = I18NManager.getSingleton().getMessaggio("insertcharges")+" "+ view.getModelUscita().getnome() + " "+I18NManager.getSingleton().getMessaggio("failed");
				Alert.segnalazioneErroreGrave(msg);
			}
		} else {
			Alert.info(I18NManager.getSingleton().getMessaggio("fillinall"), Alert.TITLE_ERROR);
		}
	}

}
