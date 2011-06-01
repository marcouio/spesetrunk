package business.comandi.gruppi;

import java.util.HashMap;

import business.cache.CacheGruppi;
import business.comandi.AbstractCommand;
import domain.AbstractOggettoEntita;
import domain.Gruppi;
import domain.IGruppi;
import domain.wrapper.IWrapperEntity;
import domain.wrapper.WrapGruppi;

public class CommandInserisciGruppo extends AbstractCommand {

	final private AbstractOggettoEntita entita;
	final private IWrapperEntity wrap;
	private HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandInserisciGruppo(final IGruppi entita) {
		final CacheGruppi cache = CacheGruppi.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapGruppi();
		this.entita = ((IWrapperEntity) entita).getentitaPadre();
	}

	@Override
	public boolean execute() {
		if (entita instanceof Gruppi) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (entita instanceof Gruppi) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserito Gruppo " + ((Gruppi) entita).getnome();
	}

}
