package business.comandi;

import java.util.HashMap;

import business.Database;
import business.cache.CacheCategorie;
import domain.AbstractOggettoEntita;
import domain.Entrate;
import domain.SingleSpesa;
import domain.wrapper.IWrapperEntity;

public abstract class AbstractCommand implements ICommand {

	protected AbstractOggettoEntita                  entita;
	protected IWrapperEntity                         wrap;
	protected HashMap<String, AbstractOggettoEntita> mappaCache;

	@Override
	public boolean doCommand(String tipo) {
		if (execute()) {
			try {
				decidiCosaAggiornare(tipo);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return true;
		} else
			return false;
	}

	@Override
	public boolean undoCommand(String tipo) {
		if (unExecute()) {
			try {
				decidiCosaAggiornare(tipo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else
			return false;
	}

	private void decidiCosaAggiornare(String tipo)
	    throws Exception {
		if (tipo != null) {
			if (tipo.equals(Entrate.NOME_TABELLA)) {
				Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
			} else if (tipo.equals(SingleSpesa.NOME_TABELLA)) {
				Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
			} else {
				Database.aggiornamentoGenerale(Entrate.NOME_TABELLA);
				Database.aggiornamentoGenerale(SingleSpesa.NOME_TABELLA);
			}
			Database.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());
		}
	}

	@Override
	public abstract boolean execute();

	@Override
	public abstract boolean unExecute();

}
