package view.impostazioni.ascoltatori;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import view.impostazioni.GruppiView;
import business.Controllore;
import business.aggiornatori.AggiornatoreManager;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheCategorie;
import business.comandi.gruppi.CommandDeleteGruppo;
import domain.Gruppi;

public class AscoltatoreEliminaGruppo extends AscoltatoreAggiornatoreTutto {

	private GruppiView gruppiView;

	public AscoltatoreEliminaGruppo(final GruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		final JComboBox comboGruppi = gruppiView.getComboGruppi();
		final Gruppi gruppi = (Gruppi) comboGruppi.getSelectedItem();

		if (comboGruppi.getSelectedIndex() != 0 && gruppi != null) {
			gruppiView.setGruppo("Cancella");
			if (Controllore.invocaComando(new CommandDeleteGruppo(gruppiView.getModelGruppi()))) {
				comboGruppi.removeItem(gruppi);
				gruppiView.dispose();
			}
		} else {
			Alert.segnalazioneErroreGrave("Impossibile cancellare un gruppo inesistente!");
		}
		AggiornatoreManager.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
	}
}
