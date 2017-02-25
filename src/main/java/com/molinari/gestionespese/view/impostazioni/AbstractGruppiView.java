package com.molinari.gestionespese.view.impostazioni;

import java.util.Observer;

import javax.swing.JDialog;

import com.molinari.gestionespese.domain.wrapper.WrapGruppi;

public abstract class AbstractGruppiView extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	protected WrapGruppi modelGruppi = null;

	public AbstractGruppiView(final WrapGruppi modelGruppi) {
		this.modelGruppi = modelGruppi;
	}

	public String getNome() {
		return modelGruppi.getnome();
	}

	public String getDescrizione() {
		return modelGruppi.getdescrizione();
	}

	public void setNome(final String nome) {
		modelGruppi.setnome(nome);
	}

	public void setDescrizione(final String descrizione) {
		modelGruppi.setdescrizione(descrizione);
	}

	public WrapGruppi getModelGruppi() {
		return modelGruppi;
	}

	public void setModelGruppi(final WrapGruppi modelGruppi) {
		this.modelGruppi = modelGruppi;
	}

}
