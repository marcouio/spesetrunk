package com.molinari.gestionespese.view.entrateuscite;

import java.util.Observer;

import javax.swing.JDialog;

import org.apache.commons.math3.util.MathUtils;

import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IUtenti;
import com.molinari.gestionespese.domain.Utenti;
import com.molinari.gestionespese.domain.wrapper.WrapSingleSpesa;
import com.molinari.utility.text.CorreggiTesto;

public abstract class AbstractUsciteView implements Observer {

	private JDialog dialog = new JDialog();
	public static final long serialVersionUID = 1L;
	private WrapSingleSpesa modelUscita = null;

	public AbstractUsciteView(final WrapSingleSpesa modelUscita) {
		this.modelUscita = modelUscita;
	}
	
	public boolean nonEsistonoCampiNonValorizzati() {
		boolean dateNotNull = getcData() != null && getDataIns() != null;
		boolean descriptionNotNull = getcNome() != null && getcDescrizione() != null;
		boolean otherInfoNotNull = getCategoria() != null && !MathUtils.equals(getdEuro(), 0.0) && getUtenti() != null;
		return descriptionNotNull && dateNotNull && otherInfoNotNull;
	}
	
	public WrapSingleSpesa getModelUscita() {
		return modelUscita;
	}

	public void setModelUscita(final WrapSingleSpesa modelUscita) {
		this.modelUscita = modelUscita;
	}

	protected String getcNome() {
		return modelUscita.getNome();
	}

	protected void setcNome(final String cNome) {
		final CorreggiTesto correttoreTesto = new CorreggiTesto(cNome);
		modelUscita.setNome(correttoreTesto.getTesto());
	}

	protected String getcData() {
		return modelUscita.getData();
	}

	protected void setcData(final String cData) {
		modelUscita.setData(cData);
	}

	protected Double getdEuro() {
		return modelUscita.getinEuro();
	}

	protected void setdEuro(final Double dEuro) {
		modelUscita.setinEuro(dEuro);
	}

	public String getcDescrizione() {
		return modelUscita.getdescrizione();
	}

	public void setcDescrizione(final String cDescrizione) {
		final CorreggiTesto correttoreTesto = new CorreggiTesto(cDescrizione);
		modelUscita.setdescrizione(correttoreTesto.getTesto());
	}

	public IUtenti getUtenti() {
		return modelUscita.getUtenti();
	}

	public void setUtenti(final Utenti utente) {
		modelUscita.setUtenti(utente);
	}

	public ICatSpese getCategoria() {
		return modelUscita.getCatSpese();
	}

	public void setCategoria(final ICatSpese catSpese) {
		modelUscita.setCatSpese(catSpese);
	}

	public String getDataIns() {
		return modelUscita.getDataIns();
	}

	public void setDataIns(final String date) {
		modelUscita.setDataIns(date);
	}

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

}
