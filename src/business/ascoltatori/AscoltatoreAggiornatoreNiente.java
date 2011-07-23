package business.ascoltatori;

import java.awt.event.ActionEvent;

import business.aggiornatori.AggiornatoreManager;

public class AscoltatoreAggiornatoreNiente extends AscoltatoreBase {

	public AscoltatoreAggiornatoreNiente() {
		super(AggiornatoreManager.AGGIORNA_NULLA);
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
	}

}
