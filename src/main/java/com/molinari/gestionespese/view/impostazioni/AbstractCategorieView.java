package com.molinari.gestionespese.view.impostazioni;

import java.util.Observer;

import javax.swing.JDialog;

import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;

public abstract class AbstractCategorieView implements Observer {

	public enum CATEGORYTYPE{
		STEADY, VARIABLE, TRIVIAL
	}
	
	private JDialog dialog = new JDialog();
	private WrapCatSpese modelCatSpese = null;

	public AbstractCategorieView(final WrapCatSpese modelCatSpese) {
		this.modelCatSpese = modelCatSpese;
	}

	protected String getcNome() {
		return modelCatSpese.getnome();
	}

	protected void setcNome(final String cNome) {
		modelCatSpese.setnome(cNome);
	}

	public String getcDescrizione() {
		return modelCatSpese.getdescrizione();
	}

	public void setcDescrizione(final String cDescrizione) {
		modelCatSpese.setdescrizione(cDescrizione);
	}

	public String getcImportanza() {
		return modelCatSpese.getimportanza();
	}

	public void setcImportanza(final String cImportanza) {
		modelCatSpese.setimportanza(cImportanza);
	}

	public IGruppi getGruppo() {
		return modelCatSpese.getGruppi();
	}

	public void setGruppo(final IGruppi gruppo) {
		modelCatSpese.setGruppi(gruppo);
	}

	public WrapCatSpese getModelCatSpese() {
		return modelCatSpese;
	}

	public void setModelCatSpese(final WrapCatSpese modelCatSpese) {
		this.modelCatSpese = modelCatSpese;
	}

	public JDialog getDialog() {
		return dialog;
	}

	public void setDialog(JDialog dialog) {
		this.dialog = dialog;
	}

}
