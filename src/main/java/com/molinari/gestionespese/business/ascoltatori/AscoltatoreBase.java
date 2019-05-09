package com.molinari.gestionespese.business.ascoltatori;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.utility.aggiornatori.IAggiornatore;
import com.molinari.utility.controller.ControlloreBase;

public abstract class AscoltatoreBase implements ActionListener {

	protected IAggiornatore aggiornatore;
	private String cosaAggiornare;
	public AscoltatoreBase(final String cosaAggiornare) {
		this.cosaAggiornare = cosaAggiornare;
	}

	public AscoltatoreBase(final IAggiornatore aggiornatore) {
		this.aggiornatore = aggiornatore;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if(aggiornatore == null) {
			aggiornatore = Controllore.getAggiornatoreManager().creaAggiornatore(cosaAggiornare);
		}
		try {
			actionPerformedOverride(e);
		} catch (final Exception e1) {
			ControlloreBase.getLog().log(Level.SEVERE, e1.getMessage(), e1);
		}
		aggiornatore.aggiorna();
	}

	/**
	 * Questo è il metodo del listener che va implementato al posto dell'actionPerformed.
	 * Occhio ad utilizzare questo e non l'altro
	 *
	 * @param e
	 * @throws Exception
	 */
	protected abstract void actionPerformedOverride(final ActionEvent e);

}
