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
import com.molinari.gestionespese.view.AlertMaker;
import com.molinari.gestionespese.view.impostazioni.AbstractCategorieView;
import com.molinari.utility.messages.I18NManager;

public class AscoltatoreInserisciCategoria extends AscoltatoreAggiornatoreTutto {

	private AbstractCategorieView categorieView;

	private AlertMaker alertMaker = new AlertMaker();
	
	public AscoltatoreInserisciCategoria() {
		//do nothing
	}
	
	public AscoltatoreInserisciCategoria(final AbstractCategorieView categorieView) {
		this.setCategorieView(categorieView);
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) {
		super.actionPerformedOverride(e);
		getCategorieView().aggiornaModelDaVista("Inserisci");
		
		if(getCategorieView().esistonoCampiNonValorizzati()) {
			sendAlertFillInAll();
		}else if(getCategorieView().categoryAlreadyExists()) {
			sendAlertSameName();
		}else {

			IGruppi gruppo = getCategorieView().getModelCatSpese().getGruppi();
			if(gruppo == null || gruppo.getidGruppo() == 0){
				gruppo = new Gruppi();
				gruppo.setnome(getCategorieView().getModelCatSpese().getNome());
				gruppo.setdescrizione(getCategorieView().getModelCatSpese().getdescrizione());
				final int idGruppo = CacheGruppi.getSingleton().getMaxId() + 1;
				gruppo.setidGruppo(idGruppo);
				gruppo.setUtenti((IUtenti) Controllore.getUtenteLogin());
				Controllore.invocaComando(new CommandInserisciGruppo(gruppo));
				
				getCategorieView().setGruppo(gruppo);
			}
			
			if (Controllore.invocaComando(new CommandInserisciCategoria(getCategorieView().getModelCatSpese()))) {
				ICatSpese categoria1 = CacheCategorie.getSingleton().getCatSpese(Integer.toString(getCategorieView().getModelCatSpese().getidCategoria()));
				if (categoria1 != null) {
					getCategorieView().getComboCategorie().addItem(categoria1);
				}
				getCategorieView().getModelCatSpese().setChanged();
				getCategorieView().getModelCatSpese().notifyObservers();
				getCategorieView().getDialog().dispose();
			}
		} 
	}

	protected void sendAlertSameName() {
		getAlertMaker().sendSevereMsg(I18NManager.getSingleton().getMessaggio("catsamename"));
	}

	protected void sendAlertFillInAll() {
		getAlertMaker().sendSevereMsg(I18NManager.getSingleton().getMessaggio("fillinall"));
	}

	public AbstractCategorieView getCategorieView() {
		return categorieView;
	}

	public void setCategorieView(AbstractCategorieView categorieView) {
		this.categorieView = categorieView;
	}

	public AlertMaker getAlertMaker() {
		return alertMaker;
	}

	public void setAlertMaker(AlertMaker alertMaker) {
		this.alertMaker = alertMaker;
	}
}
