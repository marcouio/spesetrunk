package view.impostazioni;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;

import domain.Gruppi;
import domain.wrapper.WrapCatSpese;

public class AbstractCategorieView extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	public WrapCatSpese       modelCatSpese    = null;

	public AbstractCategorieView(WrapCatSpese modelCatSpese) {
		this.modelCatSpese = modelCatSpese;
	}

	protected String getcNome() {
		return modelCatSpese.getnome();
	}

	protected void setcNome(String cNome) {
		modelCatSpese.setnome(cNome);
	}

	public String getcDescrizione() {
		return modelCatSpese.getdescrizione();
	}

	public void setcDescrizione(String cDescrizione) {
		modelCatSpese.setdescrizione(cDescrizione);
	}

	public String getcImportanza() {
		return modelCatSpese.getimportanza();
	}

	public void setcImportanza(String cImportanza) {
		modelCatSpese.setimportanza(cImportanza);
	}

	public Gruppi getGruppo() {
		return modelCatSpese.getGruppi();
	}

	public void setGruppo(Gruppi gruppo) {
		modelCatSpese.setGruppi(gruppo);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
