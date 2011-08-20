package business.comandi.singlespese;

import java.util.HashMap;

import view.Alert;
import business.cache.CacheUscite;
import business.comandi.AbstractCommand;
import domain.AbstractOggettoEntita;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.wrapper.IWrapperEntity;
import domain.wrapper.WrapSingleSpesa;

public class CommandDeleteSpesa extends AbstractCommand {

	public CommandDeleteSpesa(ISingleSpesa entita) {
		CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapSingleSpesa();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();
	}

	@Override
	public boolean execute() {
		if (entita instanceof SingleSpesa) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (entita instanceof SingleSpesa) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Eliminata Spesa " + ((SingleSpesa) entita).getnome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Cancellata correttamente spesa " + entita.getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Ripristinata spesa " + entita.getnome() + " precedentemente cancellata");
		}
	}

}