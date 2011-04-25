package view.entrateuscite;

import java.util.Observer;

import javax.swing.JDialog;

import domain.CatSpese;
import domain.Utenti;
import domain.wrapper.WrapSingleSpesa;

public abstract class AbstractUsciteView extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	public WrapSingleSpesa    modelUscita      = null;

	public AbstractUsciteView(WrapSingleSpesa modelUscita) {
		this.modelUscita = modelUscita;
	}

	protected String getcNome() {
		return modelUscita.getnome();
	}

	protected void setcNome(String cNome) {
		modelUscita.setnome(cNome);
	}

	protected String getcData() {
		return modelUscita.getData();
	}

	protected void setcData(String cData) {
		modelUscita.setData(cData);
	}

	protected Double getdEuro() {
		return modelUscita.getinEuro();
	}

	protected void setdEuro(Double dEuro) {
		modelUscita.setinEuro(dEuro);
	}

	public String getcDescrizione() {
		return modelUscita.getdescrizione();
	}

	public void setcDescrizione(String cDescrizione) {
		modelUscita.setdescrizione(cDescrizione);
	}

	public Utenti getUtenti() {
		return modelUscita.getUtenti();
	}

	public void setUtenti(Utenti utente) {
		modelUscita.setUtenti(utente);
	}

	public CatSpese getCategoria() {
		return modelUscita.getCatSpese();
	}

	public void setCategoria(CatSpese catSpese) {
		modelUscita.setCatSpese(catSpese);
	}

	public String getDataIns() {
		return modelUscita.getDataIns();
	}

	public void setDataIns(String date) {
		modelUscita.setDataIns(date);
	}

}
