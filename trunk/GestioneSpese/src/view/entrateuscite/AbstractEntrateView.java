package view.entrateuscite;

import java.util.Observer;

import javax.swing.JDialog;

import domain.Utenti;
import domain.wrapper.WrapEntrate;

public abstract class AbstractEntrateView extends JDialog implements Observer {

	private static final long serialVersionUID = 1703525939065075165L;
	public WrapEntrate        modelEntrate     = null;

	public AbstractEntrateView(WrapEntrate modelEntrate) {
		this.modelEntrate = modelEntrate;
	}

	protected String getcNome() {
		return modelEntrate.getnome();
	}

	protected void setcNome(String cNome) {
		modelEntrate.setnome(cNome);
	}

	protected String getcData() {
		return modelEntrate.getdata();
	}

	protected void setcData(String cData) {
		modelEntrate.setdata(cData);
	}

	protected Double getdEuro() {
		return modelEntrate.getinEuro();
	}

	protected void setdEuro(Double dEuro) {
		modelEntrate.setinEuro(dEuro);
	}

	public String getcDescrizione() {
		return modelEntrate.getdescrizione();
	}

	public void setcDescrizione(String cDescrizione) {
		modelEntrate.setdescrizione(cDescrizione);
	}

	public Utenti getUtenti() {
		return modelEntrate.getUtenti();
	}

	public void setUtenti(Utenti utente) {
		modelEntrate.setUtenti(utente);
	}

	public String getFisseOVar() {
		return modelEntrate.getFisseoVar();
	}

	public void setFisseOVar(String FisseVar) {
		modelEntrate.setFisseoVar(FisseVar);
	}

	public String getDataIns() {
		return modelEntrate.getDataIns();
	}

	public void setDataIns(String date) {
		modelEntrate.setDataIns(date);
	}

	public void setnEntrate(String idEntrata) {
		modelEntrate.setidEntrate(Integer.parseInt(idEntrata));
	}
}
