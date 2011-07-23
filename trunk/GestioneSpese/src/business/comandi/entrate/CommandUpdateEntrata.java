package business.comandi.entrate;

import java.util.HashMap;

import view.Alert;
import business.cache.CacheEntrate;
import business.comandi.AbstractCommand;
import business.comandi.ICommand;
import domain.AbstractOggettoEntita;
import domain.Entrate;
import domain.IEntrate;
import domain.wrapper.WrapEntrate;

public class CommandUpdateEntrata extends AbstractCommand implements ICommand {

	final private Entrate newEntita;
	final private Entrate oldEntita;
	final private WrapEntrate wrap;

	public CommandUpdateEntrata(final Entrate oldEntita, final IEntrate newEntita) {
		this.newEntita = (Entrate) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapEntrate();
		final CacheEntrate cache = CacheEntrate.getSingleton();
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

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Aggiornata correttamente entrata " + entita.getnome());
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Ripristinata categoria " + entita.getnome() + " precedentemente aggiornata");
		}
	}
}
