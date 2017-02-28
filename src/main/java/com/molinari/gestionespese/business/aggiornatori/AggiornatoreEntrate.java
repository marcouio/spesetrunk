package com.molinari.gestionespese.business.aggiornatori;

import java.util.logging.Level;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.domain.Entrate;

public class AggiornatoreEntrate extends AggiornatoreBase implements IAggiornatore {

	@Override
	public boolean aggiorna() {
		try {
			return AggiornatoreManager.aggiornamentoGenerale(Entrate.NOME_TABELLA);
		} catch (final Exception e) {
			Controllore.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}

}
