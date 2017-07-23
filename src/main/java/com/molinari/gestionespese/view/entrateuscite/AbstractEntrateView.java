package com.molinari.gestionespese.view.entrateuscite;

import java.util.Observer;

import javax.swing.JDialog;

import org.apache.commons.math3.util.MathUtils;

import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapEntrate;
import com.molinari.utility.messages.I18NManager;
import com.molinari.utility.text.CorreggiTesto;

public abstract class AbstractEntrateView implements Observer {

	public enum INCOMETYPE{
		VARIABLES, FIXITY;

		@Override
		public String toString() {
			return I18NManager.getSingleton().getMessaggio(super.toString().toLowerCase());
		}
		
	}
	
	private JDialog dialog = new JDialog() ;
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

	public IUtenti getUtenti() {
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

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

	public abstract void aggiornaModelDaVista();

	public boolean nonEsistonoCampiNonValorizzati() {
		boolean dateNotNull = getcData() != null && getDataIns() != null;
		boolean descrizioneNotNull = getcNome() != null && getcDescrizione() != null;
		boolean euroNotNull = MathUtils.equals(getdEuro(), 0.0);
		boolean sameFieldNotNull = getFisseOVar() != null && !euroNotNull;
		return descrizioneNotNull && dateNotNull && sameFieldNotNull && getUtenti() != null;
	}
}
