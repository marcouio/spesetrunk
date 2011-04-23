package business.comandi;

import java.util.HashMap;

import business.cache.CacheEntrate;
import domain.AbstractOggettoEntita;
import domain.Entrate;
import domain.IEntrate;
import domain.wrapper.WrapEntrate;

public class CommandUpdateEntrata extends AbstractCommand {

	final private Entrate     newEntita;
	final private Entrate     oldEntita;
	final private WrapEntrate wrap;

	public CommandUpdateEntrata(Entrate oldEntita, IEntrate newEntita) {
		this.newEntita = (Entrate) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapEntrate();
		CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() {
		if (newEntita instanceof Entrate) {
			if (wrap.update(newEntita)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (oldEntita instanceof Entrate) {
			if (wrap.update(oldEntita)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Entrata " + (newEntita).getnome();
	}
}
