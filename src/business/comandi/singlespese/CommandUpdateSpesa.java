package business.comandi.singlespese;

import business.cache.CacheUscite;
import command.javabeancommand.AbstractCommandForJavaBean;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.wrapper.WrapSingleSpesa;
import grafica.componenti.alert.Alert;

public class CommandUpdateSpesa extends AbstractCommandForJavaBean<SingleSpesa> {

	private SingleSpesa newEntita;
	private SingleSpesa oldEntita;
	private WrapSingleSpesa wrap;

	public CommandUpdateSpesa(final SingleSpesa oldEntita, final ISingleSpesa newEntita) {
		this.newEntita = (SingleSpesa) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapSingleSpesa();
		final CacheUscite cache = CacheUscite.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public boolean execute() {
		if (newEntita instanceof SingleSpesa && wrap.update(newEntita)) {
			mappaCache.put(Integer.toString(newEntita.getidSpesa()), newEntita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() {
		if (oldEntita instanceof SingleSpesa && wrap.update(oldEntita)) {
			mappaCache.put(Integer.toString(oldEntita.getidSpesa()), oldEntita);
			return true;
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
			Alert.segnalazioneInfo("Aggiornata correttamente spesa " + newEntita.getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata spesa " + oldEntita.getnome() + " precedentemente cancellata");
		}
	}

}
