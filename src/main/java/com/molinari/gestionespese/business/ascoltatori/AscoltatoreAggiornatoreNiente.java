package com.molinari.gestionespese.business.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;

public class AscoltatoreAggiornatoreNiente extends AscoltatoreBase {

	public AscoltatoreAggiornatoreNiente() {
		super(AggiornatoreManager.AGGIORNA_NULLA);
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		//do nothing
	}

}
