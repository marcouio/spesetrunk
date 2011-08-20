package business.aggiornatori;

public class AggiornatoreTotale extends AggiornatoreBase implements IAggiornatore {

	@Override
	public boolean aggiorna() {
		return AggiornatoreManager.aggiornamentoPerImpostazioni();
	}

}
