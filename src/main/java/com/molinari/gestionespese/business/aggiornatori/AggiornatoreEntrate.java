package com.molinari.gestionespese.business.aggiornatori;

import java.util.logging.Level;

import com.molinari.gestionespese.domain.Entrate;
import com.molinari.utility.aggiornatori.AggiornatoreBase;
import com.molinari.utility.aggiornatori.IAggiornatore;
import com.molinari.utility.controller.ControlloreBase;

public class AggiornatoreEntrate extends AggiornatoreBase implements IAggiornatore {

	@Override
	public boolean aggiorna() {
		try {
			return AggiornatoreManager.aggiornamentoGenerale(Entrate.NOME_TABELLA);
		} catch (final Exception e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return false;
	}

}
