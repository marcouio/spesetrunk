package business.aggiornatori;

import domain.SingleSpesa;

public class AggiornatoreUscite extends AggiornatoreBase implements IAggiornatore {

	@Override
	public boolean aggiorna() {
		try {
			return AggiornatoreManager.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
