package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.comandi.gruppi.CommandInserisciGruppo;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.gestionespese.view.impostazioni.GruppiView;
import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltatoreInserisciGruppo extends AscoltatoreAggiornatoreTutto {

	private final GruppiView gruppiView;

	public AscoltatoreInserisciGruppo(final GruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) {
		super.actionPerformedOverride(e);

		gruppiView.setGruppo("Inserisci");
		final WrapGruppi modelGruppi = gruppiView.getModelGruppi();

		if (gruppiView.nonEsistonoCampiNonValorizzati()) {

			if (Controllore.invocaComando(new CommandInserisciGruppo(modelGruppi))) {
				IGruppi gruppo1 = CacheGruppi.getSingleton().getGruppo(Integer.toString(modelGruppi.getidGruppo()));
				if (gruppo1 != null) {
					gruppiView.getComboGruppi().addItem(gruppo1);
				}

				modelGruppi.setChanged();
				modelGruppi.notifyObservers();
				gruppiView.getDialog().dispose();
			}
		} else {
			final String messaggio = "E' necessario riempire tutti i campi";
			Alert.segnalazioneErroreGrave(messaggio);
		}
	}

}
