package business.ascoltatori;

import java.awt.event.ActionEvent;

import business.aggiornatori.AggiornatoreManager;

public class AscoltatoreAggiornatoreUscite extends AscoltatoreBase {

	public AscoltatoreAggiornatoreUscite() {
		super(AggiornatoreManager.AGGIORNA_USCITE);
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
	}

}
