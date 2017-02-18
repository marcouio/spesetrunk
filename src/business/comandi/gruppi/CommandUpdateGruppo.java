package business.comandi.gruppi;

import grafica.componenti.alert.Alert;

import business.cache.CacheGruppi;

import command.javabeancommand.AbstractCommandForJavaBean;
import domain.Gruppi;
import domain.IGruppi;
import domain.wrapper.WrapGruppi;

public class CommandUpdateGruppo extends AbstractCommandForJavaBean<Gruppi> {

	private final Gruppi                                 newEntita;
	private final Gruppi                                 oldEntita;

	public CommandUpdateGruppo(final Gruppi oldEntita, final IGruppi newEntita) {
		this.newEntita = (Gruppi) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapGruppi();
		final CacheGruppi cache = CacheGruppi.getSingleton();
		mappaCache = cache.getCache();
	}

	@Override
	public boolean execute() throws Exception {
		if (newEntita instanceof Gruppi && wrap.update(newEntita)) {
			mappaCache.put(Integer.toString(newEntita.getidGruppo()), newEntita);
			return true;
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (oldEntita instanceof Gruppi && wrap.update(oldEntita)) {
			mappaCache.put(Integer.toString(oldEntita.getidGruppo()), oldEntita);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Gruppo " + (newEntita).getnome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornato correttamente gruppo " + newEntita.getnome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinato gruppo " + oldEntita.getnome() + " precedentemente aggiornato");
		}
	}
}
