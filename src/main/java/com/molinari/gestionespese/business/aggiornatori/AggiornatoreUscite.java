package com.molinari.gestionespese.business.aggiornatori;

import java.util.logging.Level;

import com.molinari.gestionespese.domain.SingleSpesa;

import controller.ControlloreBase;

public class AggiornatoreUscite extends AggiornatoreBase implements IAggiornatore {

	@Override
	public boolean aggiorna() {
		try {
			return AggiornatoreManager.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}
}
