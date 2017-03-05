package com.molinari.gestionespese.view.entrateuscite;

import java.util.Observer;

import javax.swing.JDialog;

import com.molinari.gestionespese.business.CorreggiTesto;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;

public abstract class AbstractEntrateView extends JDialog implements Observer {

	private static final long serialVersionUID = 1703525939065075165L;
	private WrapEntrate modelEntrate = null;

	public AbstractEntrateView(final WrapEntrate modelEntrate) {
		this.modelEntrate = modelEntrate;
	}

	public WrapEntrate getModelEntrate() {
		return modelEntrate;
	}

	public void setModelEntrate(final WrapEntrate modelEntrate) {
		this.modelEntrate = modelEntrate;
	}

	protected String getcNome() {
		return modelEntrate.getnome();
	}

	protected void setcNome(final String cNome) {
		final CorreggiTesto correttoreTesto = new CorreggiTesto(cNome);
		modelEntrate.setnome(correttoreTesto.getTesto());
	}

	protected String getcData() {
		return modelEntrate.getdata();
	}

	protected void setcData(final String cData) {
		modelEntrate.setdata(cData);
	}

	protected Double getdEuro() {
		return modelEntrate.getinEuro();
	}

	protected void setdEuro(final Double dEuro) {
		modelEntrate.setinEuro(dEuro);
	}

	public String getcDescrizione() {
		return modelEntrate.getdescrizione();
	}

	public void setcDescrizione(final String cDescrizione) {
		final CorreggiTesto correttoreTesto = new CorreggiTesto(cDescrizione);
		modelEntrate.setdescrizione(correttoreTesto.getTesto());
	}

	public Utenti getUtenti() {
		return modelEntrate.getUtenti();
	}

	public void setUtenti(final Utenti utente) {
		modelEntrate.setUtenti(utente);
	}

	public String getFisseOVar() {
		return modelEntrate.getFisseoVar();
	}

	public void setFisseOVar(final String fisseVar) {
		final CorreggiTesto correttoreTesto = new CorreggiTesto(fisseVar);
		modelEntrate.setFisseoVar(correttoreTesto.getTesto());
	}

	public String getDataIns() {
		return modelEntrate.getDataIns();
	}

	public void setDataIns(final String date) {
		modelEntrate.setDataIns(date);
	}

	public void setnEntrate(final String idEntrata) {
		modelEntrate.setidEntrate(Integer.parseInt(idEntrata));
	}
}
