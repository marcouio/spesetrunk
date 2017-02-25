package com.molinari.gestionespese.business.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;

public class AscoltatoreAggiornatoreUscite extends AscoltatoreBase {

	public AscoltatoreAggiornatoreUscite() {
		super(AggiornatoreManager.AGGIORNA_USCITE);
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
	}

}
