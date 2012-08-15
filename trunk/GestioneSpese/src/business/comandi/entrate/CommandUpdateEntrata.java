package business.comandi.entrate;

import grafica.componenti.alert.Alert;

import java.util.HashMap;

import business.cache.CacheEntrate;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;

import domain.Entrate;
import domain.IEntrate;
import domain.wrapper.WrapEntrate;

public class CommandUpdateEntrata extends AbstractCommandForJavaBean implements ICommand {

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
			Alert.segnalazioneInfo("Aggiornata correttamente entrata " + newEntita.getnome());
		}
	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinata categoria " + oldEntita.getnome() + " precedentemente aggiornata");
		}
	}
}
