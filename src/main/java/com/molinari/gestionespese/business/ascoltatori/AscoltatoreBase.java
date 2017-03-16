package com.molinari.gestionespese.business.ascoltatori;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.IAggiornatore;

import controller.ControlloreBase;

public abstract class AscoltatoreBase implements ActionListener {

	protected IAggiornatore aggiornatore;

	public AscoltatoreBase(final String cosaAggiornare) {
		aggiornatore = Controllore.getSingleton().getAggiornatoreManager().creaAggiornatore(cosaAggiornare);
	}

	public AscoltatoreBase(final IAggiornatore aggiornatore) {
		this.aggiornatore = aggiornatore;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
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
