package com.molinari.gestionespese.business.aggiornatori;

import com.molinari.gestionespese.domain.Entrate;

public class AggiornatoreEntrate extends AggiornatoreBase implements IAggiornatore {

	@Override
	public boolean aggiorna() {
		try {
			return AggiornatoreManager.aggiornamentoGenerale(Entrate.NOME_TABELLA);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
