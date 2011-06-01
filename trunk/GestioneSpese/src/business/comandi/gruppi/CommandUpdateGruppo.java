package business.comandi.gruppi;

import java.util.HashMap;

import business.cache.CacheGruppi;
import business.comandi.AbstractCommand;
import domain.AbstractOggettoEntita;
import domain.Gruppi;
import domain.IGruppi;
import domain.wrapper.WrapGruppi;

public class CommandUpdateGruppo extends AbstractCommand {

	final private Gruppi                                 newEntita;
	final private Gruppi                                 oldEntita;
	final private WrapGruppi                             wrap;
	private final HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandUpdateGruppo(final Gruppi oldEntita, final IGruppi newEntita) {
		this.newEntita = (Gruppi) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapGruppi();
		final CacheGruppi cache = CacheGruppi.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() {
		if (newEntita instanceof Gruppi) {
			if (wrap.update(newEntita)) {
				mappaCache.put(Integer.toString(newEntita.getidGruppo()), newEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (oldEntita instanceof Gruppi) {
			if (wrap.update(oldEntita)) {
				mappaCache.put(Integer.toString(oldEntita.getidGruppo()), oldEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Gruppo " + (newEntita).getnome();
	}
}
