package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.gruppi.CommandDeleteGruppo;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.view.impostazioni.GruppiView;

import grafica.componenti.alert.Alert;

public class AscoltatoreEliminaGruppo extends AscoltatoreAggiornatoreTutto {

	private final GruppiView gruppiView;

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
