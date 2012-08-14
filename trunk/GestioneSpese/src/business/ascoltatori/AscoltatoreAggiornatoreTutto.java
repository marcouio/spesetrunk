package business.ascoltatori;

import java.awt.event.ActionEvent;

import business.aggiornatori.AggiornatoreManager;

public class AscoltatoreAggiornatoreTutto extends AscoltatoreBase {

	public AscoltatoreAggiornatoreTutto() {
		super(AggiornatoreManager.AGGIORNA_ALL);
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
	}

}
