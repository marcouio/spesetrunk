package business.comandi.singlespese;

import java.util.HashMap;

import view.Alert;
import business.cache.CacheUscite;
import business.comandi.AbstractCommand;
import domain.AbstractOggettoEntita;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;

public class CommandUpdateSpesa extends AbstractCommand {

	final private SingleSpesa newEntita;
	final private SingleSpesa oldEntita;
	final private WrapSingleSpesa wrap;
	private final HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandUpdateSpesa(final SingleSpesa oldEntita, final ISingleSpesa newEntita) {
		this.newEntita = (SingleSpesa) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapSingleSpesa();
		final CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() {
		if (newEntita instanceof SingleSpesa) {
			if (wrap.update(newEntita)) {
				mappaCache.put(Integer.toString(newEntita.getidSpesa()), newEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (oldEntita instanceof SingleSpesa) {
			if (wrap.update(oldEntita)) {
				mappaCache.put(Integer.toString(oldEntita.getidSpesa()), oldEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Spesa " + (newEntita).getnome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Aggiornata correttamente spesa " + oldEntita.getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.operazioniSegnalazioneInfo("Ripristinata spesa " + oldEntita.getnome() + " precedentemente cancellata");
		}
	}

}
