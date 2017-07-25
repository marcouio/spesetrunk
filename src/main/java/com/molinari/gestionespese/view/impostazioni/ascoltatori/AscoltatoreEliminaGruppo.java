package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.gruppi.CommandDeleteGruppo;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.view.impostazioni.AbstractGruppiView;
import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltatoreEliminaGruppo extends AscoltatoreAggiornatoreTutto {

	private final AbstractGruppiView gruppiView;

	public AscoltatoreEliminaGruppo(final AbstractGruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		final JComboBox comboGruppi = gruppiView.getComboGruppi();
		final Gruppi gruppi = (Gruppi) comboGruppi.getSelectedItem();

		if (comboGruppi.getSelectedIndex() != 0 && gruppi != null) {
			gruppiView.setGruppo("Cancella");
			if (Controllore.invocaComando(new CommandDeleteGruppo(gruppiView.getModelGruppi()))) {
				comboGruppi.removeItem(gruppi);
				gruppiView.getDialog().dispose();
			}
		} else {
			Alert.segnalazioneErroreGrave("Impossibile cancellare un gruppo inesistente!");
		}
		AggiornatoreManager.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
	}
}
