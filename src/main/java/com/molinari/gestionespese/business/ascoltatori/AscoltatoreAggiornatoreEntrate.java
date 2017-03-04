package com.molinari.gestionespese.business.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;

public class AscoltatoreAggiornatoreEntrate extends AscoltatoreBase {

	public AscoltatoreAggiornatoreEntrate() {
		super(AggiornatoreManager.AGGIORNA_ENTRATE);
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		//Do nothing
	}

}
