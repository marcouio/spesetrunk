package com.molinari.gestionespese.view.datainsert;

import java.awt.event.ActionEvent;
import java.util.logging.Level;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreEntrate;
import com.molinari.gestionespese.business.comandi.entrate.CommandDeleteEntrata;
import com.molinari.utility.controller.ControlloreBase;
import com.molinari.utility.graphic.component.alert.Alert;
import com.molinari.utility.messages.I18NManager;

final class AscoltaEliminaUltimaEntrata extends AscoltatoreAggiornatoreEntrate {
	/**
	 * 
	 */
	private final PanelIncomes panelIncomes;

	/**
	 * @param panelIncomes
	 */
	AscoltaEliminaUltimaEntrata(PanelIncomes panelIncomes) {
		this.panelIncomes = panelIncomes;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) {
		super.actionPerformedOverride(e);

		try {
			this.panelIncomes.aggiornaModelDaVista();
			if (Controllore.invocaComando(new CommandDeleteEntrata(this.panelIncomes.getModelEntrate()))) {
				final String msg = I18NManager.getSingleton().getMessaggio("okentrata")+" " + this.panelIncomes.getModelEntrate().getnome() + " "+ I18NManager.getSingleton().getMessaggio("correctlydeleted");
				Alert.segnalazioneInfo(msg);
			}
		} catch (final Exception e2) {
			ControlloreBase.getLog().log(Level.SEVERE, e2.getMessage(), e2);
			Alert.segnalazioneErroreGrave(e2.getMessage());
		}
	}
}