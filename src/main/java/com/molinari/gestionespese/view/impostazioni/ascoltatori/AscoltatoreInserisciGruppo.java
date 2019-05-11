package com.molinari.gestionespese.view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import com.molinari.gestionespese.business.Controllore;
import com.molinari.gestionespese.business.ascoltatori.AscoltatoreAggiornatoreTutto;
import com.molinari.gestionespese.business.cache.CacheGruppi;
import com.molinari.gestionespese.business.comandi.gruppi.CommandInserisciGruppo;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapGruppi;
import com.molinari.gestionespese.view.AlertMaker;
import com.molinari.gestionespese.view.impostazioni.AbstractGruppiView;
import com.molinari.utility.messages.I18NManager;

public class AscoltatoreInserisciGruppo extends AscoltatoreAggiornatoreTutto {

	private AbstractGruppiView gruppiView;
	private AlertMaker alertMaker = new AlertMaker();

	public AscoltatoreInserisciGruppo() {
		
	}
	
	public AscoltatoreInserisciGruppo(final AbstractGruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) {
		super.actionPerformedOverride(e);
	
		getGruppiView().setGruppo("Inserisci");
		final WrapGruppi modelGruppi = getGruppiView().getModelGruppi();

		if (getGruppiView().esistonoCampiNonValorizzati()) {
			sendAlertFillInAll();
		}else if(getGruppiView().groupAlreadyExists()) {
			sendAlertSameName();
		}else if (invocaComando(modelGruppi)) {
			notifyChanges(modelGruppi);
		} 
	}

	protected boolean invocaComando(final WrapGruppi modelGruppi) {
		return Controllore.invocaComando(new CommandInserisciGruppo(modelGruppi));
	}

	protected void notifyChanges(final WrapGruppi modelGruppi) {
		IGruppi gruppo1 = CacheGruppi.getSingleton().getGruppo(Integer.toString(modelGruppi.getidGruppo()));
		if (gruppo1 != null) {
			getGruppiView().getComboGruppi().addItem(gruppo1);
		}

		modelGruppi.setChanged();
		modelGruppi.notifyObservers();
		getGruppiView().setModelGruppi(new WrapGruppi());
		getGruppiView().getDialog().dispose();
	}

	protected void sendAlertSameName() {
		getAlertMaker().sendSevereMsg(I18NManager.getSingleton().getMessaggio("grpsamename"));
	}

	protected void sendAlertFillInAll() {
		getAlertMaker().sendSevereMsg(I18NManager.getSingleton().getMessaggio("fillinall"));
	}
	public AbstractGruppiView getGruppiView() {
		return gruppiView;
	}
	public void setGruppiView(AbstractGruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	public AlertMaker getAlertMaker() {
		return alertMaker;
	}

	public void setAlertMaker(AlertMaker alertMaker) {
		this.alertMaker = alertMaker;
	}

}
