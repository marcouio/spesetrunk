package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheCategorie;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.comandi.categorie.CommandInserisciCategoria;
import com.molinari.gestionespese.business.comandi.gruppi.CommandInserisciGruppo;
import com.molinari.gestionespese.domain.Gruppi;
import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.view.impostazioni.AbstractCategorieView;
import com.molinari.utility.graphic.component.alert.Alert;

public class AscoltatoreInserisciCategoria extends AscoltatoreAggiornatoreTutto {

	private final AbstractCategorieView categorieView;

	public AscoltatoreInserisciCategoria(final AbstractCategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		categorieView.aggiornaModelDaVista("Inserisci");
		if (categorieView.nonEsistonoCampiNonValorizzati()) {

			IGruppi gruppo = categorieView.getModelCatSpese().getGruppi();
			if(gruppo == null || gruppo.getidGruppo() == 0){
				gruppo = new Gruppi();
				gruppo.setnome(categorieView.getModelCatSpese().getNome());
				gruppo.setdescrizione(categorieView.getModelCatSpese().getdescrizione());
				final int idGruppo = CacheGruppi.getSingleton().getMaxId() + 1;
				gruppo.setidGruppo(idGruppo);
				gruppo.setUtenti((IUtenti) Controllore.getUtenteLogin());
				Controllore.invocaComando(new CommandInserisciGruppo(gruppo));
				
				categorieView.setGruppo(gruppo);
			}
			
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
