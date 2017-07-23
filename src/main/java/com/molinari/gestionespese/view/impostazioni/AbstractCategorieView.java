package com.molinari.gestionespese.view.impostazioni;

import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import com.molinari.gestionespese.domain.ICatSpese;
import com.molinari.gestionespese.domain.IGruppi;
import com.molinari.gestionespese.domain.wrapper.WrapCatSpese;
import com.molinari.utility.messages.I18NManager;

public abstract class AbstractCategorieView implements Observer {

	public enum CATEGORYTYPE{
		STEADY, VARIABLE, TRIVIAL;
		
		@Override
		public String toString() {
			return I18NManager.getSingleton().getMessaggio(super.toString().toLowerCase());
		}
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

	public boolean nonEsistonoCampiNonValorizzati() {
		return getcDescrizione() != null && getcImportanza() != null && getcNome() != null;
	}
	
	public abstract JComboBox<ICatSpese> getComboCategorie();

	public abstract void aggiornaModelDaVista(String string);

	public abstract ICatSpese getCategoria();

}
