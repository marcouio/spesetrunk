package business.aggiornatori;

public class AggiornatoreEntrate extends AggiornatoreBase implements IAggiornatore {

	@Override
	public boolean aggiorna() {
		try {
			return AggiornatoreManager.aggiornamentoGenerale(AggiornatoreManager.AGGIORNA_ENTRATE);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
