package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.categorie.CommandUpdateCategoria;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.view.impostazioni.AbstractCategorieView;
import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltatoreAggiornaCategoria extends AscoltatoreAggiornatoreTutto {

	AbstractCategorieView categorieView;

	public AscoltatoreAggiornaCategoria(final AbstractCategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		final ICatSpese oldCategoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categorieView.getCategoria().getidCategoria()));

		if (categorieView.getComboCategorie().getSelectedItem() != null) {
			categorieView.aggiornaModelDaVista("Aggiorna");
			if (categorieView.getCategoria() != null) {
				categorieView.getModelCatSpese().setidCategoria(categorieView.getCategoria().getidCategoria());
			}
			try {
				if (Controllore.invocaComando(new CommandUpdateCategoria(oldCategoria, categorieView.getModelCatSpese().getEntitaPadre()))) {
					AggiornatoreManager.aggiornaCategorie(categorieView.getModelCatSpese().getEntitaPadre(), categorieView.getComboCategorie());
					categorieView.getModelCatSpese().setChanged();
					categorieView.getModelCatSpese().notifyObservers();
					categorieView.updateGui();
				}
			} catch (final Exception e22) {
				Alert.segnalazioneEccezione(e22, "Inserisci i dati correttamente: " + e22.getMessage());
			}
		} else {
			Alert.segnalazioneErroreGrave("Impossibile aggiornare una categoria inesistente!");
		}
	}

}
