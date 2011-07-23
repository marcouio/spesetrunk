package business.ascoltatori;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import business.Controllore;
import business.aggiornatori.IAggiornatore;

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
		actionPerformedOverride(e);
		aggiornatore.aggiorna();
	}

	protected abstract void actionPerformedOverride(final ActionEvent e);

}
