package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.aggiornatori.AggiornatoreManager;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.comandi.categorie.CommandUpdateCategoria;
import com.molinari.gestionespese.domain.CatSpese;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.view.impostazioni.CategorieView;

import grafica.componenti.alert.Alert;

public class AscoltatoreAggiornaCategoria extends AscoltatoreAggiornatoreTutto {

	CategorieView categorieView;

	public AscoltatoreAggiornaCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		final CatSpese oldCategoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categorieView.getCategoria().getidCategoria()));

		if (categorieView.getComboCategorie().getSelectedItem() != null) {
			categorieView.aggiornaModelDaVista("Aggiorna");
			if (categorieView.getCategoria() != null) {
				categorieView.getModelCatSpese().setidCategoria(categorieView.getCategoria().getidCategoria());
			}
			try {
				if (Controllore.invocaComando(new CommandUpdateCategoria(oldCategoria, (ICatSpese) categorieView.getModelCatSpese().getEntitaPadre()))) {
					AggiornatoreManager.aggiornaCategorie((CatSpese) categorieView.getModelCatSpese().getEntitaPadre(), categorieView.getComboCategorie());
					categorieView.getModelCatSpese().setChanged();
					categorieView.getModelCatSpese().notifyObservers();
					categorieView.dispose();
				}
			} catch (final Exception e22) {
				e22.printStackTrace();
				Alert.segnalazioneErroreGrave("Inserisci i dati correttamente: " + e22.getMessage());
			}
		} else {
			Alert.segnalazioneErroreGrave("Impossibile aggiornare una categoria inesistente!");
		}
	}

}
