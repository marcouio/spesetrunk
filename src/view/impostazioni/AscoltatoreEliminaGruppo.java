package view.impostazioni;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import view.Alert;
import business.Controllore;
import business.Database;
import business.cache.CacheCategorie;
import business.comandi.gruppi.CommandDeleteGruppo;
import domain.Gruppi;

public class AscoltatoreEliminaGruppo implements ActionListener {

	private GruppiView gruppiView;

	public AscoltatoreEliminaGruppo(final GruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		final JComboBox comboGruppi = gruppiView.getComboGruppi();
		final Gruppi gruppi = (Gruppi) comboGruppi.getSelectedItem();

		if (comboGruppi.getSelectedIndex() != 0 && gruppi != null) {
			gruppiView.setGruppo("Cancella");
			if (Controllore.getSingleton().getCommandManager().invocaComando(new CommandDeleteGruppo(gruppiView.getModelGruppi()), "tutto")) {
				Alert.operazioniSegnalazioneInfo("Cancellato correttamente gruppo: " + gruppi);
				comboGruppi.removeItem(gruppi);
				gruppiView.dispose();
			}
		} else {
			Alert.operazioniSegnalazioneErroreGrave("Impossibile cancellare un gruppo inesistente!");
		}
		Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
	}
}
