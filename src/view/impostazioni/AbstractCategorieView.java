package view.impostazioni;

import java.util.Observer;

import javax.swing.JDialog;

import domain.Gruppi;
import domain.wrapper.WrapCatSpese;

public abstract class AbstractCategorieView extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	public WrapCatSpese modelCatSpese = null;

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

	public Gruppi getGruppo() {
		return modelCatSpese.getGruppi();
	}

	public void setGruppo(final Gruppi gruppo) {
		modelCatSpese.setGruppi(gruppo);
	}

	public WrapCatSpese getModelCatSpese() {
		return modelCatSpese;
	}

	public void setModelCatSpese(final WrapCatSpese modelCatSpese) {
		this.modelCatSpese = modelCatSpese;
	}

}
