package com.molinari.gestionespese.business.aggiornatori;

import com.molinari.utility.aggiornatori.AggiornatoreBase;
import com.molinari.utility.aggiornatori.IAggiornatore;

public class AggiornatoreTotale extends AggiornatoreBase implements IAggiornatore {

	@Override
	public boolean aggiorna() {
		return AggiornatoreManager.aggiornamentoPerImpostazioni();
	}

}
