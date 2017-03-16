package com.molinari.gestionespese.business.aggiornatori;

@FunctionalInterface
public interface IAggiornatore {

	/**
	 * Questo è il metodo che va implementato dagli aggiornatori
	 *
	 * @return true se riesce l'aggiornamento
	 */
	boolean aggiorna();
}
