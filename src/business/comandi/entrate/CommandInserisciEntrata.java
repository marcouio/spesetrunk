package business.comandi.entrate;

import grafica.componenti.alert.Alert;

import java.util.HashMap;

import business.cache.CacheEntrate;

import command.ICommand;
import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;

import db.dao.IDAO;
import domain.Entrate;
import domain.IEntrate;
import domain.wrapper.WrapEntrate;

public class CommandInserisciEntrata extends AbstractCommandForJavaBean implements ICommand {

	private HashMap<String, AbstractOggettoEntita> mappaCache;

	public CommandInserisciEntrata(final IEntrate entita) throws Exception {
		final CacheEntrate cache = CacheEntrate.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
		this.wrap = new WrapEntrate();
		this.entita = ((IDAO) entita).getEntitaPadre();

	}

	@Override
	public boolean execute() throws Exception {
		if (entita instanceof Entrate) {
			if (wrap.insert(entita)) {
				mappaCache.put(entita.getIdEntita(), entita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (entita instanceof Entrate) {
			if (wrap.delete(Integer.parseInt(entita.getIdEntita()))) {
				mappaCache.remove(entita.getIdEntita());
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Inserita Entrata " + ((Entrate) entita).getNome();
	}

	@Override
	public void scriviLogExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Entrata " + entita.getNome() + " inserita correttamente");
		}

	}

	@Override
	public void scriviLogUnExecute(final boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Eliminata entrata " + entita.getNome() + " inserita precedentemente");
		}
	}

}
