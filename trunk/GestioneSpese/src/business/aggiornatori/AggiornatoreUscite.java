package business.aggiornatori;

public class AggiornatoreUscite extends AggiornatoreBase implements IAggiornatore {

	@Override
	public boolean aggiorna() {
		try {
			return AggiornatoreManager.aggiornamentoGenerale(AggiornatoreManager.AGGIORNA_USCITE);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
