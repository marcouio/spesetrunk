package business.comandi.entrate;

import java.util.HashMap;

import view.Alert;
import business.cache.CacheEntrate;
import business.comandi.AbstractCommand;
import business.comandi.ICommand;
import domain.AbstractOggettoEntita;
import domain.Entrate;
import domain.IEntrate;
import domain.wrapper.IWrapperEntity;
import domain.wrapper.WrapEntrate;

public class CommandDeleteEntrata extends AbstractCommand implements ICommand {

	public CommandDeleteEntrata(final IEntrate entita) {
		final CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapEntrate();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();
	}

	@Override
	public boolean execute() {
		if (entita instanceof Entrate) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (entita instanceof Entrate) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Eliminata Entrata " + ((Entrate) entita).getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Ok, entrata" + entita.getnome() + " eliminata correttamente!");
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Ok, ripristinata entrata" + entita.getnome() + " precedentemente eliminata!");
		}
	}

}
