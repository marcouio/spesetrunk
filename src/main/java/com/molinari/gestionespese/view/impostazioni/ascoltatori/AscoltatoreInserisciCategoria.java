package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.categorie.CommandInserisciCategoria;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.view.impostazioni.CategorieView;

import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltatoreInserisciCategoria extends AscoltatoreAggiornatoreTutto {

	private final CategorieView categorieView;

	public AscoltatoreInserisciCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		categorieView.aggiornaModelDaVista("Inserisci");
		if (categorieView.nonEsistonoCampiNonValorizzati()) {

			if (Controllore.invocaComando(new CommandInserisciCategoria(categorieView.getModelCatSpese()))) {
				ICatSpese categoria1 = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categorieView.getModelCatSpese().getidCategoria()));
				if (categoria1 != null) {
					categorieView.getComboCategorie().addItem(categoria1);
				}
				categorieView.getModelCatSpese().setChanged();
				categorieView.getModelCatSpese().notifyObservers();
				categorieView.getDialog().dispose();
			}
		} else {
			Alert.segnalazioneErroreGrave("E' necessario riempire tutti i campi");
		}
	}
}
