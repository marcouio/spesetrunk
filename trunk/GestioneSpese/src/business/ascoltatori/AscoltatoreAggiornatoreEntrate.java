package business.ascoltatori;

import java.awt.event.ActionEvent;

import business.aggiornatori.AggiornatoreManager;

public class AscoltatoreAggiornatoreEntrate extends AscoltatoreBase {

	public AscoltatoreAggiornatoreEntrate() {
		super(AggiornatoreManager.AGGIORNA_ENTRATE);
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {}

}
