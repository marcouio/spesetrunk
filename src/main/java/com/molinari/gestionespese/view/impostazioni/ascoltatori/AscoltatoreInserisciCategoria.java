package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.categorie.CommandInserisciCategoria;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.view.impostazioni.CategorieView;

public class AscoltatoreInserisciCategoria extends AscoltatoreAggiornatoreTutto {

	private final CategorieView categorieView;
	private CatSpese      categoria1;

	public AscoltatoreInserisciCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		categorieView.aggiornaModelDaVista("Inserisci");
		if (categorieView.nonEsistonoCampiNonValorizzati()) {

			if (Controllore.invocaComando(new CommandInserisciCategoria(categorieView.getModelCatSpese()))) {
				categoria1 = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categorieView.getModelCatSpese().getidCategoria()));
				if (categoria1 != null) {
					categorieView.getComboCategorie().addItem(categoria1);
				}
				//				Alert.operazioniSegnalazioneInfo("Inserita correttamente categoria: " + categorieView.getModelCatSpese().getnome());
				categorieView.getModelCatSpese().setChanged();
				categorieView.getModelCatSpese().notifyObservers();
				categorieView.dispose();
			}
		} else {
			Alert.segnalazioneErroreGrave("E' necessario riempire tutti i campi");
		}
	}
}
