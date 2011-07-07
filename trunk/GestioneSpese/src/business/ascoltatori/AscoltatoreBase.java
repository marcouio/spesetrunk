package business.ascoltatori;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import business.aggiornatori.IAggiornatore;
import business.aggiornatori.ManagerAggiornatore;

public class AscoltatoreBase implements ActionListener {

	IAggiornatore aggiornatore;

	public AscoltatoreBase(final String cosaAggiornare) {
		aggiornatore = ManagerAggiornatore.getSingleton().creaAggiornatore(cosaAggiornare);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		aggiornatore.aggiorna();
	}

}
